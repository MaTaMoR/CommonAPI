package me.matamor.commonapi.custominventories.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BungeeCord {

    private Plugin plugin;

    public BungeeCord(Plugin plugin) {
        this.plugin = plugin;
    }

    public void moveServer(Player player, String server) {
        Validate.notNull(plugin, "Plugin can't be null");
        Validate.notNull(player, "Player can't be null");
        Validate.notNull(server, "Server cant' be null");

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
                            out.writeUTF("Connect");
                            out.writeUTF(server);

        player.sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());
    }
}
