package me.matamor.commonapi.modules;

import me.matamor.commonapi.config.IConfig;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Module implements Plugin {

    private boolean enabled = false;
    private ModuleLoader loader = null;
    private Plugin parent = null;
    private File file = null;
    private ModuleDescription description = null;
    private File dataFolder = null;
    private ClassLoader classLoader = null;
    private ModuleLogger logger = null;
    private IConfig config = null;

    public Module() {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        if (!(classLoader instanceof ModuleClassLoader)) {
            throw new IllegalStateException("JavaPlugin requires " + ModuleClassLoader.class.getName());
        }

        ((ModuleClassLoader) classLoader).initialize(this);
    }


    protected File getFile() {
        return file;
    }

    public final File getDataFolder() {
        return dataFolder;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return this.parent.getDescription();
    }

    @Override
    public IConfig getConfig() {
        return this.config;
    }

    public final ModuleLoader getModuleLoader() {
        return this.loader;
    }

    public final Plugin getParent() {
        return this.parent;
    }

    @Override
    public final boolean isEnabled() {
        return this.enabled;
    }

    public final ModuleDescription getModuleDescription() {
        return this.description;
    }

    @Override
    public final Logger getLogger() {
        return this.logger;
    }

    @Override
    public final String getName() {
        return getModuleDescription().getName();
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
    public final InputStream getResource(String filename) {
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
    public final void saveResource(String name, boolean replace) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        name = name.replace('\\', '/');
        InputStream in = getResource(name);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + name + "' cannot be found in " + file);
        }

        File outFile = new File(this.dataFolder, name);
        int lastIndex = name.lastIndexOf('/');
        File outDir = new File(this.dataFolder, name.substring(0, lastIndex >= 0 ? lastIndex : 0));

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
    public PluginLoader getPluginLoader() {
        return this.parent.getPluginLoader();
    }

    @Override
    public Server getServer() {
        return this.parent.getServer();
    }

    @Override
    public final boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public final List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }

    @Override
    public void onLoad() {

    }

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

    final void init(ModuleLoader loader, Plugin parent, ModuleDescription description, File dataFolder, File file, ClassLoader classLoader) {
        this.parent = parent;
        this.loader = loader;
        this.file = file;
        this.description = description;
        this.dataFolder = dataFolder;
        this.classLoader = classLoader;
        this.logger = new ModuleLogger(this);
        this.config = new IConfig(this, "config.yml");
    }

    public static <T extends Module> T getModule(Class<T> clazz) {
        Validate.notNull(clazz, "Null class cannot have a Module");

        if (!Module.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz + " does not extend " + Module.class);
        }

        final ClassLoader classLoader = clazz.getClassLoader();
        if (!(classLoader instanceof ModuleClassLoader)) {
            throw new IllegalArgumentException(clazz + " is not initialized by " + ModuleClassLoader.class);
        }

        Module module = ((ModuleClassLoader) classLoader).plugin;
        if (module == null) {
            throw new IllegalStateException("Cannot get Module for " + clazz + " from a static initializer");
        }

        return clazz.cast(module);
    }
}
