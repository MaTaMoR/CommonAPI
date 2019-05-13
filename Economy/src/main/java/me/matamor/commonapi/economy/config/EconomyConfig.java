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
    @Comment("Dinero maximo que puede tener un jugador")
    public double maxMoney = 2000000;

    @Entry("Config.MinMoney")
    @Comment("Dinero minimo que puede tener un jugador")
    public double minMoney = 0;

    @Entry("Config.Vault.Enabled")
    @Comment("Registrar la economia en Vault para que otros plugins la puedan usar")
    public boolean registerVault = false;

    @Entry("Config.Vault.Account")
    @Comment("Cuenta que usara el plugin para Vault")
    public String vaultAccount = "CurrentBalanceAccount";

    @Entry("Config.ImportFromEssentials")
    @Comment("Importar dinero de Essentials cuando la cuenta se cree por primera vez")
    public boolean importFromEssentials = false;

    @Entry("Config.MoneyFormat")
    @Comment("Formatado del dinero que aparecera en los comandos")
    public String moneyFormat = "${money}";

    @Entry("Config.OfflineCache")
    @Comment({"Al cargar la informacion de un jugador desconectado esta se guarda en memoria", "si tu servidor no usa MySQL o ningun otro servidor usa la misma cuenta de Vault activa esta opcion", "al activar esta opcion el plugin es mucho mas rapido", "con transacciones de jugadores desconetados"})
    public boolean offlineCache = false;

    @Entry(value = "Config.NotificationDelay", serializer = TimeSerializer.class)
    @Comment("Delay antes de enviar las notificaciones de pago cuando el jugador se conecte")
    public long notificationDelay = TimeUnit.SECONDS.toMillis(1);

    @Entry("Config.NotificationMessage")
    @Comment("Mensaje de notificacion de pago que se envia al jugador cuando se conecta")
    public String notificationMessage = "&4&lPago &7>&8>&7> &aEl jugador &2{name} &ate pago &e{amount}&a el &2{date}&a!";

    @Entry(value = "Config.Top.Duration", serializer = TimeSerializer.class)
    @Comment("Cada cuanto se actualiza el top")
    public long topDuration = TimeUnit.MINUTES.toMillis(10);

    @Entry("Config.Top.PageSize")
    @Comment("Cuantos jugadores saldran por cada pagina del top de economia")
    public int topPageSize = 10;

    @Entry("Config.Top.HeaderFormat")
    @Comment("Formato del header del top")
    public List<String> topHeaderFormat = Arrays.asList("&6Top actualizado el ({date})", "&7-------- &cBalancetop &7---- &c{page}&7/&c{total_pages} &7----");

    @Entry("Config.Top.TotalFormat")
    @Comment("Formato del total de dinero del top")
    public String topTotalFormat = "&2Total: &e{total}";

    @Entry("Config.Top.EntryFormat")
    @Comment("Formato del top de jugadores")
    public String topEntryFormat = "&7#{position} &a{name} &e{balance}";
}
