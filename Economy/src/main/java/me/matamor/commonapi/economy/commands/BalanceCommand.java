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
    public String getUsage() {
        return super.getUsage() + " (nombre)";
    }

    @Override
    public void onCommand(CommandArgs commandArgs) throws ICommandException {
        String name;

        if (commandArgs.length == 0) {
            ifFalse(commandArgs.isPlayer(), "&cPara usar este comando desde la consola debes especificar un jugador: &4" + getUsage());

            name = commandArgs.getPlayer().getName();
        } else {
            ifFalse(Permissions.BALANCE_COMMAND_BALANCE_OTHER.hasPermission(commandArgs.getSender()), "&cNo tienes permisos para ejecutar este comando en otros jugadores!");

            name = commandArgs.getString(0);
        }

        boolean self = commandArgs.getSender().getName().equals(name);

        EconomyEntry economyEntry = getPlugin().getEconomy().getEntryOffline(name);

        notNull(economyEntry, "&cEl jugador &4%s&c no existe!", name);

        if (self) {
            notNull(economyEntry, "&cNo se ha podido cargar tu dinero, prueba volviendote a conectar!");
        } else {
            notNull(economyEntry, "&cNo se ha podido cargar el dinero del jugador: &4%s", economyEntry.getIdentifier().getName());
        }

        if (self) {
            commandArgs.sendMessage("&aBalance: &e%s", getPlugin().formatMoney(economyEntry.getBalance(getPlugin().getPluginConfig().vaultAccount)));
        } else {
            commandArgs.sendMessage("&aBalance de &2%s&a: &e%s", economyEntry.getIdentifier().getName(), getPlugin().formatMoney(economyEntry.getBalance(getPlugin().getPluginConfig().vaultAccount)));
        }
    }
}
