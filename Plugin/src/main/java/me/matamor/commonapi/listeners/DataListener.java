package me.matamor.commonapi.listeners;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import me.matamor.commonapi.CommonAPI;
import me.matamor.commonapi.storage.identifier.SimpleIdentifier;
import me.matamor.commonapi.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class DataListener implements Listener {

    @SuppressWarnings("all")
    private final Cache<String, Boolean> connectedPlayers;

    @Getter
    private final CommonAPI plugin;

    public DataListener(CommonAPI plugin) {
        this.plugin = plugin;

        this.connectedPlayers = CacheBuilder.newBuilder()
                .initialCapacity(100)
                .expireAfterWrite(1000, TimeUnit.MILLISECONDS)
                .build();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        if (this.plugin.isLoaded()) {
            if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
                String name = event.getName();

                if (this.connectedPlayers.getIfPresent(name) != null) {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, StringUtils.color("&cYou're already connecting the server!"));
                } else {
                    this.connectedPlayers.put(name, true);

                    try {
                        SimpleIdentifier identifier = this.plugin.getIdentifierManager().loadOrCreate(event.getUniqueId(), name);
                        identifier.setName(name);

                        this.plugin.getDataManager().load(identifier);
                    } catch (Exception e) {
                        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, StringUtils.color("&cThere was an error while loading your data... Try to connect again!"));

                        this.plugin.getLogger().log(Level.SEVERE, "There was an error while loading PlayerData (" + name + ", " + event.getUniqueId() + ")", e);

                        this.connectedPlayers.invalidate(name);
                    }
                }
            }
        } else {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, StringUtils.color("&cEl servidor se esta cargando!"));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.connectedPlayers.invalidate(event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //Unload data
        if (this.plugin.isEnabled()) {
            this.plugin.getDataManager().unloadAsync(player.getUniqueId());
        } else {
            this.plugin.getDataManager().unload(player.getUniqueId());
        }

        this.plugin.getIdentifierManager().remove(player.getUniqueId());
    }
}
