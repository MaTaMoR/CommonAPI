package me.matamor.commonapi.economy.commands;

import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import me.matamor.commonapi.commands.ICommandException;
import me.matamor.commonapi.economy.EconomyEntry;
import me.matamor.commonapi.economy.EconomyModule;
import me.matamor.commonapi.permissions.Permissions;

public class BalanceCommand extends ICommand<EconomyModule> {

    public BalanceCommand(EconomyModule plugin) {
        super(plugin, "balance", new String[] { "bal", "money" });
    }

    @Override
    public String getArguments() {
        return "(name)";
    }

    @Override
    public void onCommand(CommandArgs commandArgs) throws ICommandException {
        String name;

        if (commandArgs.length == 0) {
            ifFalse(commandArgs.isPlayer(), "&cYou need to specify a player to use this command from console: &4" + getUsage());

            name = commandArgs.getPlayer().getName();
        } else {
            ifFalse(Permissions.BALANCE_COMMAND_BALANCE_OTHER.hasPermission(commandArgs.getSender()), "&cYou don't have permissions to use this command on other players!");

            name = commandArgs.getString(0);
        }

        boolean self = commandArgs.getSender().getName().equals(name);

        EconomyEntry economyEntry = getPlugin().getEconomy().getEntryOffline(name);

        notNull(economyEntry, "&cThe player &4%s&c doesn't exist!", name);

        if (self) {
            notNull(economyEntry, "&cCouldn't load your money, try reconnecting!");
        } else {
            notNull(economyEntry, "&cCouldn't load the money from the player: &4%s", economyEntry.getIdentifier().getName());
        }

        if (self) {
            commandArgs.sendMessage("&aBalance: &e%s", getPlugin().formatMoney(economyEntry.getBalance(getPlugin().getPluginConfig().vaultAccount)));
        } else {
            commandArgs.sendMessage("&2%s's&a balance: &e%s", economyEntry.getIdentifier().getName(), getPlugin().formatMoney(economyEntry.getBalance(getPlugin().getPluginConfig().vaultAccount)));
        }
    }
}
