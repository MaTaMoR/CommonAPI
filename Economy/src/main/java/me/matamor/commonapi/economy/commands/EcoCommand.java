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
    public String getArguments() {
        return "<give/take/set/reset> <name> (amount)";
    }

    @Override
    public void onCommand(CommandArgs commandArgs) throws ICommandException {
        ifFalse(commandArgs.length > 1, "&cInvalid usage: &4" + commandArgs.getLabel( ));

        //Get and check the eco command type

        EcoCommandType ecoCommandType = EcoCommandType.fromString(commandArgs.getString(0));

        notNull(ecoCommandType, "&cInvalid type command: &4" + commandArgs.getString(0));

        //Get and check the target player

        String target = commandArgs.getString(1);

        handle(commandArgs, ecoCommandType, target);
    }

    private void handle(CommandArgs commandArgs, EcoCommandType ecoCommandType, String target) throws ICommandException {
        EconomyEntry targetEntry = getPlugin().getEconomy().getEntryOffline(target);

        notNull(targetEntry, "&cThe player &4%s&c doesn't exist!", target);

        if (ecoCommandType == EcoCommandType.RESET) {
            targetEntry.setBalance(getPlugin().getPluginConfig().vaultAccount, 0);

            commandArgs.sendMessage("&aThe money from the player &2%s&a has been reset!", targetEntry.getIdentifier().getName());
        } else {
            ifFalse(commandArgs.length > 2, "&4You need to specify an amount to use this command!");

            //Get and check the amount

            double amount;

            try {
                amount = commandArgs.getDouble(2);
            } catch (CastUtils.FormatException e) {
                throw new ICommandException("&cInvalid amount: &4" + commandArgs.getString(2));
            }

            //Check if balance is positive

            ifTrue(0.01 > amount, "&cThe minimum amount is &4" + getPlugin().formatMoney(0.01));

            switch (ecoCommandType) {
                case GIVE: {
                    targetEntry.addBalance(getPlugin().getPluginConfig().vaultAccount, amount);

                    commandArgs.sendMessage("&aYou gave &e%s&a to &2%s&a!", getPlugin().formatMoney(amount), targetEntry.getIdentifier().getName());
                    break;
                }
                case TAKE: {
                    targetEntry.removeBalance(getPlugin().getPluginConfig().vaultAccount, amount);

                    commandArgs.sendMessage("&aYou took &e%s&a from &2%s&a!", getPlugin().formatMoney(amount), targetEntry.getIdentifier().getName());
                    break;
                }
                case SET: {
                    targetEntry.setBalance(getPlugin().getPluginConfig().vaultAccount, amount);

                    commandArgs.sendMessage("&2%s&a now has &e%s&a!", targetEntry.getIdentifier().getName(), getPlugin().formatMoney(amount));
                    break;
                }
                default: {
                    commandArgs.sendMessage("&cInvalid action: &4" + ecoCommandType.name());
                    break;
                }
            }
        }
    }
}
