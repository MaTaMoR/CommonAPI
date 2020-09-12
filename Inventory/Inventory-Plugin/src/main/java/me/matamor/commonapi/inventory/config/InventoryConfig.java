package me.matamor.commonapi.inventory.config;

import me.matamor.commonapi.config.special.Comment;
import me.matamor.commonapi.config.special.CommentedConfig;
import me.matamor.commonapi.config.special.Entry;
import me.matamor.commonapi.inventory.InventoryModule;

import java.io.File;

public class InventoryConfig extends CommentedConfig {

    public InventoryConfig(InventoryModule plugin) {
        super(plugin, new File(plugin.getDataFolder(), "config.yml"));
    }

    @Entry("Server.Offline")
    @Comment("Messages showed when a server is offline!")
    public String offlineServer = "&cOffline";

    @Entry("Server.Online")
    @Comment("Messages showed when a server is online!")
    public String onlineServer = "&aOnline";
}
