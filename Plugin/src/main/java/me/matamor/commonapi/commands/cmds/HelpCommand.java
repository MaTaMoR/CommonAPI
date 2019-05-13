package me.matamor.commonapi.commands.cmds;

import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class HelpCommand<T extends Plugin> extends ICommand {

    public HelpCommand(ICommand parent) {
        super(parent.getPlugin(), "help", "help command", parent, new String[] { });
    }

    @Override
    public void onCommand(CommandArgs commandArgs) {
        Collection<ICommand> children = getParent().getChildren();

        if (children.isEmpty()) {
            commandArgs.sendMessage("&cThere are no sub-commands");
        } else {
            commandArgs.sendMessage("&8------------------ &8[&6&l" + getParent().getName() + "&8] ------------------");

            children.forEach(c -> commandArgs.sendMessage("&a%s &8- &f%s", commandArgs.getOtherUsage(c), c.getDescription()));
        }
    }
}
