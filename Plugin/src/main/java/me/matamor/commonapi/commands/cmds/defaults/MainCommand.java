package me.matamor.commonapi.commands.cmds.defaults;

import me.matamor.commonapi.CommonAPI;
import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import me.matamor.commonapi.commands.cmds.HelpCommand;
import me.matamor.commonapi.permissions.IPermission;

public class MainCommand extends ICommand<CommonAPI> {

    public MainCommand(CommonAPI plugin) {
        super(plugin, "commonapi", new String[] { });

        setPermission(new IPermission("commandapi.admin"));

        addChild(new IdentifierCommand(plugin));

        setHelpCommand(new HelpCommand(this));
    }

    @Override
    public void onCommand(CommandArgs commandArgs) {

    }
}
