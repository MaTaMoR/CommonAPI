package me.matamor.commonapi.economy.listener;

import lombok.Getter;
import me.matamor.commonapi.economy.EconomyEntry;
import me.matamor.commonapi.economy.EconomyModule;
import me.matamor.commonapi.economy.mini.Arguments;
import me.matamor.commonapi.economy.mini.Mini;
import me.matamor.commonapi.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

public class EconomyListener implements Listener {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss yyyy-mm-dd");

    @Getter
    private final EconomyModule plugin;

    @Getter
    private Mini mini;

    public EconomyListener(EconomyModule plugin) {
        this.plugin = plugin;

        if (this.plugin.getPluginConfig().importFromIConomy) {
            File miniFile = new File("plugins/iConomy/");

            if (miniFile.exists()) {
                this.mini = new Mini(miniFile.getPath(), "accounts.mini");
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        EconomyEntry economyEntry = this.plugin.getEconomy().getEntry(player.getUniqueId());

        if (economyEntry != null) {
            if (this.plugin.getPluginConfig().importFromIConomy && this.mini != null && economyEntry.isNewAccount()) {
                Arguments arguments = this.mini.getArguments(player.getName());
                if (arguments == null) {
                    arguments = this.mini.getArguments(player.getUniqueId());
                }

                if (arguments != null) {
                    double oldBalance = arguments.getDouble("balance");

                    if (oldBalance > 0) {
                        economyEntry.addBalance(this.plugin.getPluginConfig().vaultAccount, oldBalance);

                        this.plugin.getLogger().log(Level.INFO, "[EconomyImporter] Old balance (" + oldBalance + ") from player (" + player.getName() + ") has been imported from iConomy!");
                    }

                    //Delete the entry from the database
                    this.mini.removeIndex(arguments.getKey());
                }
            }

            if (economyEntry.hasNotifications()) {
                this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                    if (player.isOnline()) {
                        economyEntry.getNotifications().forEach(notification ->
                                player.sendMessage(StringUtils.color(this.plugin.getPluginConfig().notificationMessage
                                        .replace("{name}", notification.getName())
                                        .replace("{date}", DATE_FORMAT.format(notification.getDate()))
                                        .replace("{amount}", this.plugin.formatMoney(notification.getAmount())))));

                        economyEntry.clearNotifications();
                    }
                }, this.plugin.getPluginConfig().notificationDelay / 50);
            }
        }

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
