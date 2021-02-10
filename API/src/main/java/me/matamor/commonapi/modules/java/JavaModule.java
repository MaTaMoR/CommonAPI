package me.matamor.commonapi.modules.java;

import me.matamor.commonapi.config.IConfig;
import me.matamor.commonapi.modules.Module;
import me.matamor.commonapi.modules.ModuleLogger;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaModule implements Module {

    private boolean enabled = false;
    private JavaModuleLoader loader = null;
    private Plugin plugin;
    private File file = null;
    private PluginDescriptionFile description = null;
    private File dataFolder = null;
    private ClassLoader classLoader = null;
    private ModuleLogger logger = null;
    private IConfig config = null;

    public JavaModule() {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        if (!(classLoader instanceof ModuleClassLoader)) {
            throw new IllegalStateException("Module requires " + ModuleClassLoader.class.getName());
        }

        ((ModuleClassLoader) classLoader).initialize(this);
    }


    protected File getFile() {
        return file;
    }

    public final @NotNull File getDataFolder() {
        return dataFolder;
    }

    @Override
    public @NotNull PluginDescriptionFile getDescription() {
        return this.description;
    }

    @Override
    public @NotNull IConfig getConfig() {
        return this.config;
    }

    public final JavaModuleLoader getModuleLoader() {
        return this.loader;
    }

    @Override
    public final boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public final @NotNull Logger getLogger() {
        return this.logger;
    }

    @Override
    public final @NotNull String getName() {
        return this.description.getName();
    }

    public final ClassLoader getClassLoader() {
        return this.classLoader;
    }

    @Override
    public boolean isNaggable() {
        throw new RuntimeException("[Module] Not supported operation");
    }

    @Override
    public void setNaggable(boolean b) {
        throw new RuntimeException("[Module] Not supported operation");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String s, String s1) {
        throw new RuntimeException("[Module] Not supported operation");
    }

    @Override
    public final InputStream getResource(@NotNull String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        try {
            URL url = getClassLoader().getResource(filename);

            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void saveConfig() {
        getConfig().save();
    }

    @Override
    public void saveDefaultConfig() {
        getConfig().save();
    }

    @Override
    public final void saveResource(@NotNull String name, boolean replace) {
        if (name.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        name = name.replace('\\', '/');
        InputStream in = getResource(name);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + name + "' cannot be found in " + file);
        }

        File outFile = new File(this.dataFolder, name);
        int lastIndex = name.lastIndexOf('/');
        File outDir = new File(this.dataFolder, name.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } else {
                this.logger.log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        } catch (IOException ex) {
            this.logger.log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

    @Override
    public void reloadConfig() {
        getConfig().reload();
    }

    @Override
    public @NotNull PluginLoader getPluginLoader() {
        return this.plugin.getPluginLoader();
    }

    @Override
    public @NotNull Server getServer() {
        return this.plugin.getServer();
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] strings) {
        return false;
    }

    @Override
    public final List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] strings) {
        return new ArrayList<>();
    }

    @Override
    public final void onLoad() { }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    protected final void setEnabled(final boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;

            if (this.enabled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }

    final void init(JavaModuleLoader loader, Plugin plugin, PluginDescriptionFile description, File dataFolder, File file, ClassLoader classLoader) {
        this.plugin = plugin;
        this.loader = loader;
        this.file = file;
        this.description = description;
        this.dataFolder = dataFolder;
        this.classLoader = classLoader;
        this.logger = new ModuleLogger(this);
        this.config = new IConfig(this, "config.yml");
    }

    public static <T extends JavaModule> T getModule(Class<T> clazz) {
        Validate.notNull(clazz, "Null class cannot have a Module");

        if (!JavaModule.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz + " does not extend " + JavaModule.class);
        }

        final ClassLoader classLoader = clazz.getClassLoader();
        if (!(classLoader instanceof ModuleClassLoader)) {
            throw new IllegalArgumentException(clazz + " is not initialized by " + ModuleClassLoader.class);
        }

        JavaModule module = ((ModuleClassLoader) classLoader).plugin;
        if (module == null) {
            throw new IllegalStateException("Cannot get Module for " + clazz + " from a static initializer");
        }

        return clazz.cast(module);
    }
}
