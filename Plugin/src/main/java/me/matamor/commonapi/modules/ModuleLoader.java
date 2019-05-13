package me.matamor.commonapi.modules;

import me.matamor.commonapi.events.ModuleDisableEvent;
import me.matamor.commonapi.events.ModuleEnableEvent;
import me.matamor.commonapi.modules.exception.ModuleException;
import me.matamor.commonapi.utils.Reflections;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class ModuleLoader {

    private final Pattern[] fileFilters = new Pattern[] { Pattern.compile("\\.jar$"), };
    private final Map<String, Class<?>> classes = new HashMap<>();
    private final Map<String, ModuleClassLoader> loaders = new LinkedHashMap<>();

    private final Map<String, Class<?>> pluginClasses;

    final Plugin plugin;

    public ModuleLoader(Plugin plugin) {
        this.plugin = plugin;

        ClassLoader loader = plugin.getClass().getClassLoader();
        this.pluginClasses = Reflections.getField(loader.getClass(), "classes", Map.class).get(loader);
    }

    public Module[] loadModules() {
        File folder = new File(this.plugin.getDataFolder(), "Modules");
        folder.mkdirs();

        Set<Module> modules = new HashSet<>();

        if (!folder.exists() || !folder.isDirectory()) {
            this.plugin.getLogger().info("[ModuleManager] There are no Modules to loadOrCreate!");
            return new Module[] { };
        }

        File[] files = folder.listFiles();
        if (files == null) {
            this.plugin.getLogger().info("[ModuleManager] There are no Modules to loadOrCreate!");
            return new Module[] { };
        }

        for (File file : files) {
            boolean valid = false;

            for (Pattern pattern : this.fileFilters) {
                valid = pattern.matcher(file.getName()).find();
            }

            if (valid) {
                modules.add(loadModule(file));
            }
        }

        return modules.toArray(new Module[modules.size()]);
    }

    public Module loadModule(final File file) {
        if(!file.exists()) throw new RuntimeException(new FileNotFoundException(file.getPath() + " does not exist"));

        final ModuleDescription description;
        try {
            description = getModuleDescription(file);
        } catch (ModuleException ex) {
            throw new RuntimeException(ex);
        }

        final File parentFile = file.getParentFile();
        final File dataFolder = new File(parentFile, description.getName());

        if (dataFolder.exists() && !dataFolder.isDirectory()) {
            throw new RuntimeException(String.format("Projected datafolder: `%s' for %s (%s) exists and is not a directory", dataFolder, description.getName(), file));
        }

        final ModuleClassLoader loader;
        try {
            loader = new ModuleClassLoader(this, getClass().getClassLoader(), description, dataFolder, file);
        } catch (InvalidPluginException | IOException e) {
            throw new ModuleException(e);
        }

        this.loaders.put(description.getName(), loader);

        return loader.plugin;
    }

    public ModuleDescription getModuleDescription(File file) {
        Validate.notNull(file, "File cannot be null");

        JarFile jar = null;
        InputStream stream = null;

        try {
            jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("plugin.yml");

            if (entry == null) {
                throw new RuntimeException(new FileNotFoundException("Jar does not contain plugin.yml"));
            }

            stream = jar.getInputStream(entry);

            return new ModuleDescription(stream);
        } catch (IOException | YAMLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (jar != null) {
                try {
                    jar.close();
                } catch (IOException ignored) {
                }
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public Pattern[] getPluginFileFilters() {
        return this.fileFilters.clone();
    }

    Class<?> getClassByName(final String name) {
        Class<?> cachedClass = this.classes.get(name);

        if (cachedClass != null) {
            return cachedClass;
        } else {
            for (String current : this.loaders.keySet()) {
                ModuleClassLoader loader = this.loaders.get(current);

                try {
                    cachedClass = loader.findClass(name, false);
                } catch (ClassNotFoundException ignored) {}
                if (cachedClass != null) return cachedClass;
            }
        }

        return null;
    }


    void setClass(final String name, final Class<?> clazz) {
        if (!this.classes.containsKey(name)) {
            this.classes.put(name, clazz);

            if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.registerClass(serializable);
            }

            setClassPlugin(name, clazz, true);
        }
    }

    private void removeClass(String name) {
        Class<?> clazz = this.classes.remove(name);

        try {
            if ((clazz != null) && (ConfigurationSerializable.class.isAssignableFrom(clazz))) {
                Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.unregisterClass(serializable);
            }

            setClassPlugin(name, clazz, false);
        } catch (NullPointerException ignored) {

        }
    }

    private void setClassPlugin(String name, Class<?> clazz, boolean add) {
        if(add) {
            this.pluginClasses.putIfAbsent(name, clazz);
        } else {
            this.pluginClasses.remove(name);
        }
    }

    public void enableModule(final Module module) {
        if (!module.isEnabled()) {
            this.plugin.getLogger().info("[ModuleManager] Enabling " + module.getModuleDescription().getName());

            String pluginName = module.getModuleDescription().getName();

            if (!this.loaders.containsKey(pluginName)) {
                this.loaders.put(pluginName, (ModuleClassLoader) module.getClassLoader());
            }

            try {
                module.setEnabled(true);
            } catch (Exception e) {
                this.plugin.getLogger().log(Level.SEVERE, "[ModuleManager] Error occurred while enabling " + module.getModuleDescription().getName(), e);
                return;
            }

            this.plugin.getServer().getPluginManager().callEvent(new ModuleEnableEvent(module));
        }
    }

    public void disableModule(Module module) {
        if (module.isEnabled()) {
            this.plugin.getLogger().info(String.format("[ModuleManager] Disabling %s", this.plugin.getDescription().getName()));

            this.plugin.getServer().getPluginManager().callEvent(new ModuleDisableEvent(module));

            ClassLoader classLoader = module.getClassLoader();

            try {
                module.setEnabled(false);
            } catch (Exception e) {
                this.plugin.getLogger().log(Level.SEVERE, "[ModuleManager] Error occurred while disabling " + module.getModuleDescription().getName(), e);
            }

            this.loaders.remove(module.getModuleDescription().getName());

            if (classLoader instanceof ModuleClassLoader) {
                ModuleClassLoader loader = (ModuleClassLoader) classLoader;
                Set<String> names = loader.getClasses();

                for (String name : names) {
                    removeClass(name);
                }
            }
        }
    }
}
