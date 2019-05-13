package me.matamor.commonapi.custominventories.utils.server;

import me.matamor.commonapi.messages.Messages;
import me.matamor.commonapi.custominventories.utils.Settings;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class ServerManager {

    private final Map<String, ServerInfo> servers = new ConcurrentHashMap<>();

    private BukkitTask task;

    public ServerManager(final Plugin plugin) {
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<ServerInfo> iterator = servers.values().iterator();
                long now = System.currentTimeMillis();

                while (iterator.hasNext()) {
                    ServerInfo info = iterator.next();

                    if (info.getLastUpdate() != 0 && System.currentTimeMillis() - info.getLastUpdate() > 600000) {
                        iterator.remove();
                        plugin.getLogger().log(Level.SEVERE, "Removed server " + info.getName() + " from tracking due to inactivity ");
                    } else if (info.should()) {
                        boolean displayOffline = true;

                        info.setLastUpdate(now);

                        try {
                            PingResponse data = ServerPinger.fetchData(info.getServerAddress(), 1000);

                            if (data.isOnline()) {
                                displayOffline = false;

                                info.setOnline(true);
                                info.setOnlinePlayers(data.getOnlinePlayers());
                                info.setMaxPlayers(data.getMaxPlayers());
                                info.setMotd(data.getMotd());
                            }
                        } catch (UnknownHostException | ConnectException | SocketTimeoutException e) {
                            if (Settings.LOG_ON_SERVER_DOWN.get()) {
                                plugin.getLogger().log(Level.SEVERE, "Couldn't fetch data from " + info.getName() + "(" + info.getServerAddress() + ") connection exception");
                            }
                        } catch (Exception e) {
                            plugin.getLogger().log(Level.SEVERE, "Couldn't fetch data from " + info.getName() + "(" + info.getServerAddress() + "), unhandled exception: ", e);
                        }

                        if (displayOffline) {
                            info.setOnline(false);
                            info.setOnlinePlayers(0);
                            info.setMaxPlayers(0);
                            info.setMotd(Messages.OFFLINE.get());
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 0);
    }

    public synchronized void register(ServerInfo info) {
        if (this.servers.containsKey(info.getName())) {
            throw new RuntimeException("The server " + info.getName() + " is already registered");
        }

        this.servers.put(info.getName(), info);
    }

    public synchronized void unregister(String name) {
        servers.remove(name);
    }

    public ServerInfo getServer(String name) {
        return servers.get(name);
    }

    public Collection<ServerInfo> getServers() {
        return Collections.unmodifiableCollection(servers.values());
    }

    public boolean isActive() {
        return task != null;
    }

    public synchronized void clear() {
        servers.clear();
    }

    public synchronized void stop() {
        if (isActive()) {
            task.cancel();
            task = null;
        }
    }
}
