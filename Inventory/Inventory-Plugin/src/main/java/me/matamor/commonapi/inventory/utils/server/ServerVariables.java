package me.matamor.commonapi.inventory.utils.server;

import me.matamor.commonapi.inventory.InventoryModule;
import me.matamor.commonapi.utils.replacement.PlayerTextVariable;
import me.matamor.commonapi.utils.replacement.PlayerVariables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerVariables {

    private static final Pattern ONLINE_PATTERN = makePlaceholder("online");
    private static final Pattern MAX_PATTERN = makePlaceholder("max_players");
    private static final Pattern MOTD_PATTERN = makePlaceholder("motd");
    private static final Pattern STATUS_PATTERN = makePlaceholder("status");

    private static Pattern makePlaceholder(String prefix) {
        return Pattern.compile("(\\{" + Pattern.quote(prefix) + ":)(.+?)(\\})");
    }

    private static String extractValue(Matcher matcher) {
        return matcher.group(2).trim();
    }

    public static PlayerTextVariable ONLINE_VARIABLE = PlayerVariables.register((text, value) -> {
        Matcher matcher = ONLINE_PATTERN.matcher(text);
        while (matcher.find()) {
            String targetServer = matcher.group(2);
            ServerInfo serverInfo = InventoryModule.getInstance().getServerManager().getServer(targetServer);

            String onlinePlayers;
            if (serverInfo == null || !serverInfo.isOnline()) {
                onlinePlayers = getOffline();
            } else {
                onlinePlayers = String.valueOf(serverInfo.getOnlinePlayers());
            }

            text = matcher.replaceFirst(onlinePlayers);
            matcher = ONLINE_PATTERN.matcher(text);
        }

        return text;
    });

    public static PlayerTextVariable MAX_VARIABLE = PlayerVariables.register((text, value) -> {
        Matcher matcher = MAX_PATTERN.matcher(text);
        while (matcher.find()) {
            String targetServer = matcher.group(2);
            ServerInfo serverInfo = InventoryModule.getInstance().getServerManager().getServer(targetServer);

            String onlinePlayers;
            if (serverInfo == null || !serverInfo.isOnline()) {
                onlinePlayers = getOffline();
            } else {
                onlinePlayers = String.valueOf(serverInfo.getMaxPlayers());
            }

            text = matcher.replaceFirst(onlinePlayers);
            matcher = MAX_PATTERN.matcher(text);
        }

        return text;
    });

    public static PlayerTextVariable MOTD_VARIABLE = PlayerVariables.register((text, value) -> {
        Matcher matcher = MOTD_PATTERN.matcher(text);
        while (matcher.find()) {
            String targetServer = matcher.group(2);
            ServerInfo serverInfo = InventoryModule.getInstance().getServerManager().getServer(targetServer);

            String onlinePlayers;
            if (serverInfo == null || !serverInfo.isOnline()) {
                onlinePlayers = getOffline();
            } else {
                onlinePlayers = serverInfo.getMotd();
            }

            text = matcher.replaceFirst(onlinePlayers);
            matcher = MOTD_PATTERN.matcher(text);
        }

        return text;
    });

    public static PlayerTextVariable STATUS_VARIABLE = PlayerVariables.register((text, value) -> {
        Matcher matcher = STATUS_PATTERN.matcher(text);
        while (matcher.find()) {
            String targetServer = matcher.group(2);
            ServerInfo serverInfo = InventoryModule.getInstance().getServerManager().getServer(targetServer);

            String onlinePlayers;
            if (serverInfo == null || !serverInfo.isOnline()) {
                onlinePlayers = getOffline();
            } else {
                onlinePlayers = getOnline();
            }

            text = matcher.replaceFirst(onlinePlayers);
            matcher = STATUS_PATTERN.matcher(text);
        }

        return text;
    });

    private static String getOffline() {
        return InventoryModule.getInstance().getPluginConfig().offlineServer;
    }


    private static String getOnline() {
        return InventoryModule.getInstance().getPluginConfig().onlineServer;
    }
}
