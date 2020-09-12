package me.matamor.commonapi.nms;

import org.bukkit.plugin.Plugin;

public interface NMSController {

    NMSVersion getVersion();

    default void initialize(Plugin plugin) throws Exception {

    }

}
