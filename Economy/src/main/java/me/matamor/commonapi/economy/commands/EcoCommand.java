package me.matamor.commonapi.economy.commands;

import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import me.matamor.commonapi.commands.ICommandException;
import me.matamor.commonapi.economy.EconomyEntry;
import me.matamor.commonapi.economy.EconomyModule;
import me.matamor.commonapi.permissions.Permissions;
import me.matamor.commonapi.utils.CastUtils;

public class EcoCommand extends ICommand<EconomyModule> {

    private enum EcoCommandType {

        GIVE,
        TAKE,
        SET,
        RESET;

        public static EcoCommandType fromString(String name) {
            for (EcoCommandType ecoCommandType : values()) {
                if (ecoCommandType.name().equalsIgnoreCase(name)) {
                    return ecoCommandType;
                }
            }

            return null;
        }
    }

    public EcoCommand(EconomyModule plugin) {
        super(plugin, "eco", new String[] { });

        setPermission(Permissions.BALANCE_COMMAND_ECO);
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <give/take/set/reset> <nombre> (cantidad)";
    }

    @Override
    public void onCommand(CommandArgs commandArgs) throws ICommandException {
        ifFalse(commandArgs.length > 1, "&cUso incorrecto: &4" + commandArgs.getSelfUsage(this));

        //Get and check the eco command type

        EcoCommandType ecoCommandType = EcoCommandType.fromString(commandArgs.getString(0));

        notNull(ecoCommandType, "&cTipo de comando invalido: &4" + commandArgs.getString(0));

        //Get and check the target player

        String target = commandArgs.getString(1);

        handle(commandArgs, ecoCommandType, target);
    }

    private void handle(CommandArgs commandArgs, EcoCommandType ecoCommandType, String target) throws ICommandException {
        EconomyEntry targetEntry = getPlugin().getEconomy().getEntryOffline(target);

        notNull(targetEntry, "&cEl jugador &4%s&c no existe!", target);

        if (ecoCommandType == EcoCommandType.RESET) {
            targetEntry.setBalance(getPlugin().getPluginConfig().vaultAccount, 0);

            commandArgs.sendMessage("&aHas reiniciado el dinero de &2%s&a!", targetEntry.getIdentifier().getName());
        } else {
            ifFalse(commandArgs.length > 2, "&4Tiene ques especificar una cantidad!");

            //Get and check the amount

            double amount;

            try {
                amount = commandArgs.getDouble(2);
            } catch (CastUtils.FormatException e) {
                throw new ICommandException("&cCantidad invalida: &4" + commandArgs.getString(2));
            }

            //Check if balance is positive

            ifFalse(1 > amount, "&cLa cantidad minima es &41&c");

            switch (ecoCommandType) {
                case GIVE: {
                    targetEntry.addBalance(getPlugin().getPluginConfig().vaultAccount, amount);

                    commandArgs.sendMessage("&aLe has dado &e%s&a a &2%s&a!", getPlugin().formatMoney(amount), targetEntry.getIdentifier().getName());
                    break;
                }
                case TAKE: {
                    targetEntry.removeBalance(getPlugin().getPluginConfig().vaultAccount, amount);

                    commandArgs.sendMessage("&aLe has quitado &e%s&a a &2%s&a!", getPlugin().formatMoney(amount), targetEntry.getIdentifier().getName());
                    break;
                }
                case SET: {
                    targetEntry.setBalance(getPlugin().getPluginConfig().vaultAccount, amount);

                    commandArgs.sendMessage("&2%s&a ahora tiene &e%s&a!", targetEntry.getIdentifier().getName(), getPlugin().formatMoney(amount));
                    break;
                }
                default: {
                    commandArgs.sendMessage("&cAccion invalida: &4" + ecoCommandType.name());
                    break;
                }
            }
        }
    }
}
