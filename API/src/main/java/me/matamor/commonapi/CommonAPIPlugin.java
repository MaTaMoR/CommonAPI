package me.matamor.commonapi;

import me.matamor.commonapi.utils.Validate;
import org.bukkit.plugin.Plugin;

public class CommonAPIPlugin {

    private CommonAPIPlugin() {

    }

    private static Plugin plugin;

    public static Plugin getPlugin() {
        Validate.notNull(plugin, "Plugin hasn't been initialized!");

        return plugin;
    }

    public static void setPlugin(Plugin plugin) {
        Validate.ifFalse(CommonAPIPlugin.plugin == null, "Plugin is already initialized!");
        Validate.ifTrue(plugin == null, "Plugin can't be null!");

        CommonAPIPlugin.plugin = plugin;
    }

    public static void removePlugin() {
        Validate.ifTrue(plugin == null, "Plugin is not initialized!");

        CommonAPIPlugin.plugin = null;
    }
}
