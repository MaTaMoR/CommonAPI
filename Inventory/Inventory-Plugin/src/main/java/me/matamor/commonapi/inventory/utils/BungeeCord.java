package me.matamor.commonapi.inventory.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public class BungeeCord {

    private Plugin plugin;

    public BungeeCord(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    public void moveServer(@NotNull Player player, @NotNull String server) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            out.write("Connect".getBytes());
            out.write(server.getBytes());

            player.sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Couldn't connect Player '" + player.getName() + "' to the Server '" + server + "'", e);
        }
    }
}
