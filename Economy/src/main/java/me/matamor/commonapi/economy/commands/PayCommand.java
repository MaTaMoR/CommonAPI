package me.matamor.commonapi.economy.commands;

import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import me.matamor.commonapi.commands.ICommandException;
import me.matamor.commonapi.economy.EconomyEntry;
import me.matamor.commonapi.economy.EconomyModule;
import me.matamor.commonapi.economy.PaymentNotification;
import me.matamor.commonapi.permissions.Permissions;
import me.matamor.commonapi.utils.CastUtils;
import org.bukkit.entity.Player;

public class PayCommand extends ICommand<EconomyModule> {

    public PayCommand(EconomyModule plugin) {
        super(plugin, "pay", new String[] { });

        setPermission(Permissions.BALANCE_COMMAND_PAY);
        setOnlyInGame(true);
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <nombre> <cantidad>";
    }

    @Override
    public void onCommand(CommandArgs commandArgs) throws ICommandException {
        ifFalse(commandArgs.length != 2, "&cUso incorrecto: &4" + getUsage());

        Player player = commandArgs.getPlayer();
        String targetName = commandArgs.getString(0);

        double amount;

        try {
            amount = commandArgs.getDouble(1);
        } catch (CastUtils.FormatException e) {
            throw new ICommandException("&cCantidad invalida: &4" + commandArgs.getString(1));
        }

        //Check if balance is positive

        ifFalse(1 > amount, "&cLa cantidad minima es &41&c");

        //Check player money is loaded

        EconomyEntry playerEntry = getPlugin().getEconomy().getEntry(player.getUniqueId());

        notNull(playerEntry, "&cNo se ha podido cargar tu dinero, prueba volviendote a conectar!");

        //Check if player has enough money

        ifTrue(playerEntry.getBalance(getPlugin().getPluginConfig().vaultAccount) >= amount, "&cNo tienes suficiente dinero!");

        //Check target money is loaded

        EconomyEntry targetEntry = getPlugin().getEconomy().getEntryOffline(targetName);

        notNull(targetEntry, "&cEl jugador &4%s&c no existe!", targetName);

        ifTrue(targetEntry.getIdentifier().equals(playerEntry.getIdentifier()), "&cNo te puedes pagar a ti mismo!");

        notNull(targetEntry, "&cNo se ha pdodio cargar el dinero de &4%s&c!", targetEntry.getIdentifier().getName());

        double extra = targetEntry.addBalance(getPlugin().getPluginConfig().vaultAccount, amount);
        double totalPaid = amount - extra;

        //Check if anything was paid

        ifTrue(totalPaid == 0, "&cNo se ha podido pagar a &4%s&c porque tiene el maximo de dinero!", targetEntry.getIdentifier().getName());

        playerEntry.removeBalance(getPlugin().getPluginConfig().vaultAccount, totalPaid);

        Player target = getPlugin().getServer().getPlayer(targetName);
        if (target == null) {
            targetEntry.addNotification(new PaymentNotification(player.getName(), System.currentTimeMillis(), totalPaid));
        } else {
            commandArgs.sendMessage(target, "&2%s&a te ha pagado &e%s", player.getName(), getPlugin().formatMoney(totalPaid));
        }

        if (extra > 0) {
            commandArgs.sendMessage("&cHas pagado a &4%s&c solo &4%s&c porque su dinero llego al maximo, no se le pudo pagar los &4%s&c restantes!", targetEntry.getIdentifier().getName(), getPlugin().formatMoney(totalPaid), getPlugin().formatMoney(amount - totalPaid));
        } else {
            commandArgs.sendMessage("&aHas pagado a &2%s&a &e%s", targetEntry.getIdentifier().getName(), getPlugin().formatMoney(amount));
        }
    }
}
