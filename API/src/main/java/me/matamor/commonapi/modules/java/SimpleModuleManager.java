package me.matamor.commonapi.modules.java;

import me.matamor.commonapi.modules.Module;
import me.matamor.commonapi.modules.ModuleLoader;
import me.matamor.commonapi.modules.ModuleManager;
import me.matamor.commonapi.modules.exception.InvalidModuleException;
import me.matamor.commonapi.utils.Reflections;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.Server;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleModuleManager implements ModuleManager {

    private final Plugin parent;
    private final Server server;

    private final Map<Pattern, ModuleLoader> fileAssociations = new HashMap<>();
    private final List<Module> modules = new ArrayList<>();
    private final Map<String, Module> lookupNames = new HashMap<>();
    @SuppressWarnings("UnstableApiUsage")
    private File updateDirectory;

    public SimpleModuleManager(@NotNull Plugin parent) {
        this.parent = parent;
        this.server = parent.getServer();
    }

    @Override
    public void registerInterface(@NotNull Class<? extends ModuleLoader> loader) throws IllegalArgumentException {
        ModuleLoader instance;

        if (ModuleLoader.class.isAssignableFrom(loader)) {
            Constructor<? extends ModuleLoader> constructor;

            try {
                constructor = loader.getConstructor(Plugin.class);
                instance = constructor.newInstance(this.parent);
            } catch (NoSuchMethodException ex) {
                String className = loader.getName();

                throw new IllegalArgumentException(String.format("Class %s does not have a public %s(Server) constructor", className, className), ex);
            } catch (Exception ex) {
                throw new IllegalArgumentException(String.format("Unexpected exception %s while attempting to construct a new instance of %s", ex.getClass().getName(), loader.getName()), ex);
            }
        } else {
            throw new IllegalArgumentException(String.format("Class %s does not implement interface PluginLoader", loader.getName()));
        }

        Pattern[] patterns = instance.getModuleFileFilters();

        synchronized (this) {
            for (Pattern pattern : patterns) {
                fileAssociations.put(pattern, instance);
            }
        }
    }

    @Override
    @NotNull
    public JavaModule[] loadModules(@NotNull File directory) {
        Validate.notNull(directory, "Directory cannot be null");
        Validate.isTrue(directory.isDirectory(), "Directory must be a directory");

        List<Module> result = new ArrayList<>();
        Set<Pattern> filters = fileAssociations.keySet();

        if (!(server.getUpdateFolder().equals(""))) {
            updateDirectory = new File(directory, server.getUpdateFolder());
        }

        Map<String, File> modules = new HashMap<>();
        Set<String> loadedModules = new HashSet<>();
        Map<String, String> ModulesProvided = new HashMap<>();
        Map<String, Collection<String>> dependencies = new HashMap<>();
        Map<String, Collection<String>> softDependencies = new HashMap<>();

        // This is where it figures out all possible Modules
        for (File file : directory.listFiles()) {
            ModuleLoader loader = null;
            for (Pattern filter : filters) {
                Matcher match = filter.matcher(file.getName());
                if (match.find()) {
                    loader = fileAssociations.get(filter);
                }
            }

            if (loader == null) continue;

            PluginDescriptionFile description = loader.getModuleDescription(file);;
            String name = description.getName();
            if (name.equalsIgnoreCase("bukkit") || name.equalsIgnoreCase("minecraft") || name.equalsIgnoreCase("mojang")) {
                server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': Restricted Name");
                continue;
            } else if (description.getRawName().indexOf(' ') != -1) {
                server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': uses the space-character (0x20) in its name");
                continue;
            }

            File replacedFile = modules.put(description.getName(), file);
            if (replacedFile != null) {
                server.getLogger().severe(String.format(
                        "Ambiguous Module name `%s' for files `%s' and `%s' in `%s'",
                        description.getName(),
                        file.getPath(),
                        replacedFile.getPath(),
                        directory.getPath()
                ));
            }

            String removedProvided = ModulesProvided.remove(description.getName());
            if (removedProvided != null) {
                server.getLogger().warning(String.format(
                        "Ambiguous Module name `%s'. It is also provided by `%s'",
                        description.getName(),
                        removedProvided
                ));
            }

            Collection<String> softDependencySet = description.getSoftDepend();
            if (softDependencySet != null && !softDependencySet.isEmpty()) {
                if (softDependencies.containsKey(description.getName())) {
                    // Duplicates do not matter, they will be removed together if applicable
                    softDependencies.get(description.getName()).addAll(softDependencySet);
                } else {
                    softDependencies.put(description.getName(), new LinkedList<>(softDependencySet));
                }
            }

            Collection<String> dependencySet = description.getDepend();
            if (dependencySet != null && !dependencySet.isEmpty()) {
                dependencies.put(description.getName(), new LinkedList<>(dependencySet));
            }

            Collection<String> loadBeforeSet = description.getLoadBefore();
            if (loadBeforeSet != null && !loadBeforeSet.isEmpty()) {
                for (String loadBeforeTarget : loadBeforeSet) {
                    if (softDependencies.containsKey(loadBeforeTarget)) {
                        softDependencies.get(loadBeforeTarget).add(description.getName());
                    } else {
                        // softDependencies is never iterated, so 'ghost' Modules aren't an issue
                        Collection<String> shortSoftDependency = new LinkedList<>();
                        shortSoftDependency.add(description.getName());
                        softDependencies.put(loadBeforeTarget, shortSoftDependency);
                    }
                }
            }
        }

        while (!modules.isEmpty()) {
            boolean missingDependency = true;
            Iterator<Map.Entry<String, File>> moduleIterator = modules.entrySet().iterator();

            while (moduleIterator.hasNext()) {
                Map.Entry<String, File> entry = moduleIterator.next();
                String Module = entry.getKey();

                if (dependencies.containsKey(Module)) {
                    Iterator<String> dependencyIterator = dependencies.get(Module).iterator();

                    while (dependencyIterator.hasNext()) {
                        String dependency = dependencyIterator.next();

                        // Dependency loaded
                        if (loadedModules.contains(dependency)) {
                            dependencyIterator.remove();

                            // We have a dependency not found
                        } else if (!modules.containsKey(dependency) && !ModulesProvided.containsKey(dependency)) {
                            missingDependency = false;
                            moduleIterator.remove();
                            softDependencies.remove(Module);
                            dependencies.remove(Module);

                            server.getLogger().log(
                                    Level.SEVERE,
                                    "Could not load '" + entry.getValue().getPath() + "' in folder '" + directory.getPath() + "'",
                                    new InvalidModuleException("Unknown dependency " + dependency + ". Please download and install " + dependency + " to run this Module."));
                            break;
                        }
                    }

                    if (dependencies.containsKey(Module) && dependencies.get(Module).isEmpty()) {
                        dependencies.remove(Module);
                    }
                }
                if (softDependencies.containsKey(Module)) {
                    // Soft depend is no longer around
                    softDependencies.get(Module).removeIf(softDependency -> !modules.containsKey(softDependency) && !ModulesProvided.containsKey(softDependency));

                    if (softDependencies.get(Module).isEmpty()) {
                        softDependencies.remove(Module);
                    }
                }
                if (!(dependencies.containsKey(Module) || softDependencies.containsKey(Module)) && modules.containsKey(Module)) {
                    // We're clear to load, no more soft or hard dependencies left
                    File file = modules.get(Module);
                    moduleIterator.remove();
                    missingDependency = false;

                    try {
                        Module loadedModule = loadModule(file);
                        if (loadedModule != null) {
                            result.add(loadedModule);
                            loadedModules.add(loadedModule.getName());
                        } else {
                            server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'");
                        }
                    } catch (InvalidModuleException ex) {
                        server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
                    }
                }
            }

            if (missingDependency) {
                // We now iterate over Modules until something loads
                // This loop will ignore soft dependencies
                moduleIterator = modules.entrySet().iterator();

                while (moduleIterator.hasNext()) {
                    Map.Entry<String, File> entry = moduleIterator.next();
                    String Module = entry.getKey();

                    if (!dependencies.containsKey(Module)) {
                        softDependencies.remove(Module);
                        missingDependency = false;
                        File file = entry.getValue();
                        moduleIterator.remove();

                        try {
                            Module loadedModule = loadModule(file);
                            if (loadedModule != null) {
                                result.add(loadedModule);
                                loadedModules.add(loadedModule.getName());
                            } else {
                                server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'");
                            }
                            break;
                        } catch (InvalidModuleException ex) {
                            server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
                        }
                    }
                }
                // We have no Modules left without a depend
                if (missingDependency) {
                    softDependencies.clear();
                    dependencies.clear();
                    Iterator<File> failedModuleIterator = modules.values().iterator();

                    while (failedModuleIterator.hasNext()) {
                        File file = failedModuleIterator.next();
                        failedModuleIterator.remove();
                        server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': circular dependency detected");
                    }
                }
            }
        }

        return result.toArray(new JavaModule[result.size()]);
    }

    @Override
    @Nullable
    public synchronized Module loadModule(@NotNull File file) throws InvalidModuleException {
        Validate.notNull(file, "File cannot be null");

        checkUpdate(file);

        Set<Pattern> filters = fileAssociations.keySet();
        Module result = null;

        for (Pattern filter : filters) {
            String name = file.getName();
            Matcher match = filter.matcher(name);

            if (match.find()) {
                ModuleLoader loader = fileAssociations.get(filter);

                result = loader.loadModule(file);
            }
        }

        if (result != null) {
            modules.add(result);
            lookupNames.put(result.getDescription().getName(), result);
        }

        return result;
    }

    private void checkUpdate(@NotNull File file) {
        if (updateDirectory == null || !updateDirectory.isDirectory()) {
            return;
        }

        File updateFile = new File(updateDirectory, file.getName());
        if (updateFile.isFile() && FileUtil.copy(updateFile, file)) {
            updateFile.delete();
        }
    }

    @Override
    @Nullable
    public synchronized Module getModule(@NotNull String name) {
        return lookupNames.get(name.replace(' ', '_'));
    }

    @Override
    @NotNull
    public synchronized Module[] getModules() {
        return modules.toArray(new JavaModule[modules.size()]);
    }

    @Override
    public boolean isModuleEnabled(@NotNull String name) {
        Module module = getModule(name);

        return isModuleEnabled(module);
    }

    @Override
    public boolean isModuleEnabled(@Nullable Module module) {
        if ((module != null) && (modules.contains(module))) {
            return module.isEnabled();
        } else {
            return false;
        }
    }

    @Override
    public void enableModule(@NotNull final Module plugin) {
        if (!plugin.isEnabled()) {
            try {
                plugin.getModuleLoader().enableModule(plugin);
            } catch (Throwable ex) {
                server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }

            HandlerList.bakeAll();
        }
    }

    @Override
    public void disableModules() {
        Module[] plugins = getModules();
        for (int i = plugins.length - 1; i >= 0; i--) {
            disableModule(plugins[i]);
        }
    }

    @Override
    public void disableModule(@NotNull final Module plugin) {
        if (plugin.isEnabled()) {
            try {
                plugin.getModuleLoader().disableModule(plugin);
            } catch (Throwable ex) {
                server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }

            try {
                server.getScheduler().cancelTasks(plugin);
            } catch (Throwable ex) {
                server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while cancelling tasks for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }

            try {
                server.getServicesManager().unregisterAll(plugin);
            } catch (Throwable ex) {
                server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering services for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }

            try {
                HandlerList.unregisterAll(plugin);
            } catch (Throwable ex) {
                server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering events for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }

            try {
                server.getMessenger().unregisterIncomingPluginChannel(plugin);
                server.getMessenger().unregisterOutgoingPluginChannel(plugin);
            } catch (Throwable ex) {
                server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering plugin channels for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
        }
    }

    public void inject(JavaPlugin plugin) {
        Reflections.MethodInvoker methodInvoker = Reflections.getMethod(JavaPlugin.class, "getClassLoader");
        Object classLoader = methodInvoker.invoke(plugin);

        //This part is a try to replace the map into something that will use the Modules class loaders aswell
        Reflections.FieldAccessor<Map> fieldAccessor = Reflections.getField(classLoader.getClass(), "classes", Map.class);
        Map<String, Class<?>> newClasses = new ConcurrentHashMap<String, Class<?>>() {

            @Override
            public Class<?> get(Object key) {
                Class<?> clazz = super.get(key);

                if (clazz == null && key instanceof String) {
                    clazz = findClass((String) key);
                }

                return clazz;
            }
        };

        Map<?, ?> map = fieldAccessor.get(classLoader);
        map.entrySet().stream()
                .filter(e -> e.getKey() instanceof String && e.getValue() instanceof Class)
                .forEach(e -> newClasses.put((String) e.getKey(), (Class<?>) e.getValue()));

        fieldAccessor.set(classLoader, newClasses);
    }

    private Class<?> findClass(String name) {
        for (ModuleLoader moduleLoader : this.fileAssociations.values()) {
            Class<?> clazz = moduleLoader.getClassByName(name);
            if (clazz != null) {
                return clazz;
            }
        }

        return null;
    }
}
