package me.matamor.commonapi.inventory.utils.server;

import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

public class ServerInfo {

    @Getter
    private final ServerAddress serverAddress;

    @Getter
    private final String name;

    @Getter
    private final int interval;

    @Getter
    private final long millisInterval;

    @Getter @Setter
    private long lastUpdate;

    @Getter @Setter
    private boolean online;

    @Getter @Setter
    private String motd;

    @Getter @Setter
    private int onlinePlayers;

    @Getter @Setter
    private int maxPlayers;

    public ServerInfo(InetSocketAddress serverAddress, String name, int interval) {
        this(new ServerAddress(serverAddress.getHostString(), serverAddress.getPort()), name, interval);
    }

    public ServerInfo(ServerAddress serverAddress, String name, int interval) {
        this.serverAddress = serverAddress;
        this.name = name;
        this.interval = interval;
        this.millisInterval = interval * 50;

        this.online = false;
        this.motd = "&cOffline";
        this.onlinePlayers = 0;
        this.maxPlayers = 0;
    }

    public boolean shouldUpdate() {
        return System.currentTimeMillis() >= (this.millisInterval + this.lastUpdate);
    }
}