package me.matamor.commonapi.economy.listener;

import lombok.Getter;
import me.matamor.commonapi.economy.EconomyEntry;
import me.matamor.commonapi.economy.EconomyModule;
import me.matamor.commonapi.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class EconomyListener implements Listener {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss yyyy-mm-dd");

    @Getter
    private final EconomyModule plugin;

    private final Logger logger;

    public EconomyListener(EconomyModule plugin) {
        this.plugin = plugin;

        this.logger = Logger.getLogger("EconomyErrors");
        this.logger.setUseParentHandlers(false);

        File logFolder = new File(this.plugin.getDataFolder() + File.separator + "Logs");
        if (!logFolder.exists()) {
            logFolder.mkdirs();
        }

        File file = new File(logFolder,"economy_errors.log");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileHandler fileHandler = new FileHandler(file.getPath());
            fileHandler.setFormatter(new SimpleFormatter());

            this.logger.addHandler(fileHandler);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't add file logger handler!", e);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
            if (player.isOnline()) {
                EconomyEntry economyEntry = this.plugin.getEconomy().getEntry(player.getUniqueId());

                if (economyEntry != null && economyEntry.hasNotifications()) {
                    economyEntry.getNotifications().forEach(notification ->
                            player.sendMessage(StringUtils.color(this.plugin.getPluginConfig().notificationMessage
                                .replace("{name}", notification.getName())
                                .replace("{date}", DATE_FORMAT.format(notification.getDate()))
                                .replace("{amount}", this.plugin.formatMoney(notification.getAmount())))));

                    economyEntry.clearNotifications();
                }
            }
        }, this.plugin.getPluginConfig().notificationDelay / 50);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.plugin.getPluginConfig().offlineCache) return;

        Player player = event.getPlayer();

        this.plugin.getEconomy().remove(player.getUniqueId());
    }

    public BigDecimal getBigDecimal(String value, BigDecimal def) {
        return toBigDecimal(value, def);
    }

    public static BigDecimal toBigDecimal(final String input, final BigDecimal def) {
        if (input == null || input.isEmpty()) {
            return def;
        } else {
            try {
                return new BigDecimal(input, MathContext.DECIMAL128);
            } catch (NumberFormatException | ArithmeticException e) {
                return def;
            }
        }
    }
}
