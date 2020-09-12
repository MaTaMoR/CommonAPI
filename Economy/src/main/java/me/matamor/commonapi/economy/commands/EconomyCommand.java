package me.matamor.commonapi.economy.commands;

import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import me.matamor.commonapi.commands.ICommandException;
import me.matamor.commonapi.commands.cmds.HelpCommand;
import me.matamor.commonapi.economy.EconomyModule;

public class EconomyCommand extends ICommand<EconomyModule> {

    public EconomyCommand(EconomyModule plugin) {
        super(plugin, "economy", new String[0]);

        setHelpCommand(new HelpCommand<EconomyModule>(this));

        //Balance command
        BalanceCommand balanceCommand = new BalanceCommand(plugin);
        balanceCommand.register();

        addChild(balanceCommand);

        //Balance top command
        BalanceTopCommand topCommand = new BalanceTopCommand(plugin);
        topCommand.register();

        addChild(topCommand);

        //Pay command
        PayCommand payCommand = new PayCommand(plugin);
        payCommand.register();

        addChild(payCommand);

        //Eco command
        EcoCommand ecoCommand = new EcoCommand(plugin);
        ecoCommand.register();

        addChild(ecoCommand);
    }

    @Override
    public void onCommand(CommandArgs commandArgs) throws ICommandException {

    }
}
