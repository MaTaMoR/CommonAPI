package me.matamor.commonapi.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

@RequiredArgsConstructor
public class NMSClassLoader<T> {

    @Getter
    private final Plugin plugin;

    @Getter
    private final String classPath;

    @Getter
    private T value;

    public boolean isLoaded() {
        return this.value != null;
    }

    public boolean load() {
        if (isLoaded()) {
            throw new IllegalStateException("Class is already loaded!");
        }

        String classPath = this.classPath.replace("{version}", ReflectionUtil.getVersion());

        try {
            this.plugin.getLogger().info("Trying to load NMSClass from: " + classPath);

            Class<?> nmsControllerClazz = Class.forName(classPath);
            Constructor<?> constructor = nmsControllerClazz.getConstructor();

            this.value = (T) constructor.newInstance();

            this.plugin.getLogger().info("NMSClass '" + classPath +  "' successfully loaded!");
        } catch (Exception ex) {
            /* Couldn't instantiate the nmsController - Spigot/CraftBukkit version isn't supported */
            this.plugin.getLogger().log(Level.SEVERE, "The plugin couldn't create the NMS controller instance and has been disabled. This is likely" +
                    "due to no supported implementation for your CraftBukkit/Spigot version.", ex);
            return false;
        }

        return true;
    }
}
