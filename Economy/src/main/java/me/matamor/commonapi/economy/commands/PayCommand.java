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
    public String getArguments() {
        return "<name> <amount>";
    }

    @Override
    public void onCommand(CommandArgs commandArgs) throws ICommandException {
        ifTrue(commandArgs.length != 2, "&cInvalid usage: &4" + commandArgs.getLabel());

        Player player = commandArgs.getPlayer();
        String targetName = commandArgs.getString(0);

        double amount;

        try {
            amount = commandArgs.getDouble(1);
        } catch (CastUtils.FormatException e) {
            throw new ICommandException("&cInvalid amount: &4" + commandArgs.getString(1));
        }

        //Check if balance is positive

        ifTrue(0.01 > amount, "&cThe minimum amount is &4" + getPlugin().formatMoney(0.01));

        //Check player money is loaded

        EconomyEntry playerEntry = getPlugin().getEconomy().getEntry(player.getUniqueId());

        notNull(playerEntry, "&cCouldn't load your money, try reconnecting!");

        //Check if player has enough money

        ifFalse(playerEntry.getBalance(getPlugin().getPluginConfig().vaultAccount) >= amount, "&cYou don't have enough money!");

        //Check target money is loaded

        EconomyEntry targetEntry = getPlugin().getEconomy().getEntryOffline(targetName);

        notNull(targetEntry, "&cEl jugador &4%s&c no existe!", targetName);

        //ifTrue(targetEntry.getIdentifier().equals(playerEntry.getIdentifier()), "&cYou can't pay to yourself!");

        notNull(targetEntry, "&cCouldn't load the money from &4%s&c!", targetEntry.getIdentifier().getName());

        double extra = targetEntry.addBalance(getPlugin().getPluginConfig().vaultAccount, amount);
        double totalPaid = amount - extra;

        //Check if anything was paid

        ifTrue(totalPaid == 0, "&cCouldn't pay to &4%s&c because he already has the maximum money!", targetEntry.getIdentifier().getName());

        playerEntry.removeBalance(getPlugin().getPluginConfig().vaultAccount, totalPaid);

        Player target = getPlugin().getServer().getPlayer(targetName);
        if (target == null) {
            targetEntry.addNotification(new PaymentNotification(player.getName(), System.currentTimeMillis(), totalPaid));
        } else {
            commandArgs.sendMessage(target, "&2%s&a paid you &e%s", player.getName(), getPlugin().formatMoney(totalPaid));
        }

        if (extra > 0) {
            commandArgs.sendMessage("&cYou only paid to &4%s&c &4%s&c because his money reached the maximum, couldn't pay the remaining &4%s&c!", targetEntry.getIdentifier().getName(), getPlugin().formatMoney(totalPaid), getPlugin().formatMoney(amount - totalPaid));
        } else {
            commandArgs.sendMessage("&aYou paid &2%s &e%s", targetEntry.getIdentifier().getName(), getPlugin().formatMoney(amount));
        }
    }
}
