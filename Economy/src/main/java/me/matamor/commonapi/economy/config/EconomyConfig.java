package me.matamor.commonapi.economy.config;

import me.matamor.commonapi.config.special.Comment;
import me.matamor.commonapi.config.special.CommentedConfig;
import me.matamor.commonapi.config.special.Entry;
import me.matamor.commonapi.utils.serializer.TimeSerializer;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EconomyConfig extends CommentedConfig {

    public EconomyConfig(Plugin plugin) {
        super(plugin, new File(plugin.getDataFolder(), "config.yml"));
    }

    @Entry("Config.MaxMoney")
    @Comment("Maximum money a player can have!")
    public double maxMoney = 2000000;

    @Entry("Config.MinMoney")
    @Comment("Minimum money a player can have!")
    public double minMoney = 0;

    @Entry("Config.Vault.Enabled")
    @Comment("Register economy into Vault, enable this to have compatibility with other plugins!")
    public boolean registerVault = false;

    @Entry("Config.Vault.Account")
    @Comment({"This plugins supports multiple accounts, this one will be used for Vault!", "If you have different economies in the same database you should change this!"})
    public String vaultAccount = "CurrentBalanceAccount";

    @Entry("Config.ImportFromIConomy")
    @Comment("Import money from iConomy when the account is created!")
    public boolean importFromIConomy = false;

    @Entry("Config.MoneyFormat")
    @Comment("Money's format, if you enable the custom format below this won't be needed!")
    public String moneyFormat = "${money}";

    @Entry("Config.OfflineCache")
    @Comment({"When a player leaves the server it's information is usually unloaded", "if you enable this option the player information won't be unloaded", "the information will still be removed but only on the cleanup"})
    public boolean offlineCache = false;

    @Entry(value = "Config.NotificationDelay", serializer = TimeSerializer.class)
    @Comment("Delay before sending the notifications when a player connects to the server!")
    public long notificationDelay = TimeUnit.SECONDS.toMillis(1);

    @Entry("Config.NotificationMessage")
    @Comment("Payment notification sent to the player when he connects to the server")
    public String notificationMessage = "&4&lPayment &7>&8>&7> &aThe player &2{name}&a paid you &e{amount}&a on &2{date}&a!";

    @Entry(value = "Config.Top.Duration", serializer = TimeSerializer.class)
    @Comment("How often is the economy top updated")
    public long topDuration = TimeUnit.MINUTES.toMillis(10);

    @Entry("Config.Top.PageSize")
    @Comment("How many players will be displayed on each economy top page")
    public int topPageSize = 10;

    @Entry("Config.Top.HeaderFormat")
    @Comment("This is the header format for the economy top")
    public List<String> topHeaderFormat = Arrays.asList("&6Last economy top update ({date})", "&7-------- &cBalancetop &7---- &c{page}&7/&c{total_pages} &7----");

    @Entry("Config.Top.TotalFormat")
    @Comment("The format of the total server money, this will display the total money of all the players in the server")
    public String topTotalFormat = "&2Total: &e{total}";

    @Entry("Config.Top.EntryFormat")
    @Comment("Format used to display each entry of the top")
    public String topEntryFormat = "&7#{position} &a{name} &e{balance}";

    @Entry("Config.Format.Enabled")
    @Comment("Enable custom format system ?")
    public boolean formatEnabled = false;

    @Entry("Config.Format.Major")
    @Comment("Minor currency")
    public String major = "Silver";

    @Entry("Config.Format.Minor")
    @Comment("Minor currency")
    public String minor = "Copper";


}
