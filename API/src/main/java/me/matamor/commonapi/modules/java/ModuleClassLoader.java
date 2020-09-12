package me.matamor.commonapi.modules.java;

import com.google.common.io.ByteStreams;
import me.matamor.commonapi.modules.exception.InvalidModuleException;
import me.matamor.commonapi.modules.java.JavaModule;
import me.matamor.commonapi.modules.java.JavaModuleLoader;
import me.matamor.commonapi.nms.NMSVersion;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * A ClassLoader for plugins, to allow shared classes across multiple plugins
 */
final class ModuleClassLoader extends URLClassLoader {
    
    private final JavaModuleLoader loader;
    private final Map<String, Class<?>> classes = new ConcurrentHashMap<>();
    private final PluginDescriptionFile description;
    private final File dataFolder;
    private final File file;
    private final JarFile jar;
    private final Manifest manifest;
    private final URL url;
    final JavaModule plugin;
    private JavaModule pluginInit;
    private IllegalStateException pluginState;

    static {
        System.out.println(
        ClassLoader.registerAsParallelCapable()
        );
    }

    ModuleClassLoader(@NotNull final JavaModuleLoader loader, @Nullable final ClassLoader parent, @NotNull final PluginDescriptionFile description, @NotNull final File dataFolder, @NotNull final File file) throws IOException, InvalidModuleException {
        super(new URL[] {file.toURI().toURL()}, parent);

        Validate.notNull(loader, "Loader cannot be null");

        this.loader = loader;
        this.description = description;
        this.dataFolder = dataFolder;
        this.file = file;
        this.jar = new JarFile(file);
        this.manifest = jar.getManifest();
        this.url = file.toURI().toURL();

        try {
            Class<?> jarClass;
            try {
                jarClass = Class.forName(description.getMain(), true, this);
            } catch (ClassNotFoundException ex) {
                throw new InvalidModuleException("Cannot find main class `" + description.getMain() + "'", ex);
            }

            Class<? extends JavaModule> pluginClass;
            try {
                pluginClass = jarClass.asSubclass(JavaModule.class);
            } catch (ClassCastException ex) {
                throw new InvalidModuleException("main class `" + description.getMain() + "' does not extend Module", ex);
            }

            plugin = pluginClass.newInstance();
        } catch (IllegalAccessException ex) {
            throw new InvalidModuleException("No public constructor", ex);
        } catch (InstantiationException ex) {
            throw new InvalidModuleException("Abnormal plugin type", ex);
        }
    }

    @Override
    public URL getResource(String name) {
        return findResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        return findResources(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, true);
    }

    Class<?> findClass(@NotNull String name, boolean checkGlobal) throws ClassNotFoundException {
        if (name.startsWith("org.bukkit.") || name.startsWith("net.minecraft.")) {
            throw new ClassNotFoundException(name);
        }

        Class<?> result = classes.get(name);

        if (result == null) {
            if (checkGlobal) {
                result = loader.getClassByName(name);
            }

            if (result == null) {
                String path = name.replace('.', '/').concat(".class");
                JarEntry entry = jar.getJarEntry(path);

                if (entry != null) {
                    byte[] classBytes;

                    try (InputStream is = jar.getInputStream(entry)) {
                        classBytes = ByteStreams.toByteArray(is);
                    } catch (IOException ex) {
                        throw new ClassNotFoundException(name, ex);
                    }

                    if (NMSVersion.isGreaterEqualThan(NMSVersion.v1_13_R1)) {
                        classBytes = loader.server.getUnsafe().processClass(description, path, classBytes);
                    }

                    int dot = name.lastIndexOf('.');
                    if (dot != -1) {
                        String pkgName = name.substring(0, dot);
                        if (getPackage(pkgName) == null) {
                            try {
                                if (manifest != null) {
                                    definePackage(pkgName, manifest, url);
                                } else {
                                    definePackage(pkgName, null, null, null, null, null, null, null);
                                }
                            } catch (IllegalArgumentException ex) {
                                if (getPackage(pkgName) == null) {
                                    throw new IllegalStateException("Cannot find package " + pkgName);
                                }
                            }
                        }
                    }

                    CodeSigner[] signers = entry.getCodeSigners();
                    CodeSource source = new CodeSource(url, signers);

                    result = defineClass(name, classBytes, 0, classBytes.length, source);
                }

                if (result == null) {
                    result = super.findClass(name);
                }

                if (result != null) {
                    loader.setClass(name, result);
                }

                classes.put(name, result);
            }
        }

        return result;
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            jar.close();
        }
    }

    @NotNull
    Set<String> getClasses() {
        return classes.keySet();
    }

    synchronized void initialize(@NotNull JavaModule Module) {
        Validate.notNull(Module, "Initializing plugin cannot be null");
        Validate.isTrue(Module.getClass().getClassLoader() == this, "Cannot initialize plugin outside of this class loader");
        if (this.plugin != null || this.pluginInit != null) {
            throw new IllegalArgumentException("Plugin already initialized!", pluginState);
        }

        pluginState = new IllegalStateException("Initial initialization");
        this.pluginInit = Module;

        Module.init(loader, loader.server, description, dataFolder, file, this);
    }
}