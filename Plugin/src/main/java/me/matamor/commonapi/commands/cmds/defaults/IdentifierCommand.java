package me.matamor.commonapi.commands.cmds.defaults;

import me.matamor.commonapi.CommonAPI;
import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import me.matamor.commonapi.storage.identifier.SimpleIdentifier;
import org.bukkit.entity.Player;

public class IdentifierCommand extends ICommand<CommonAPI> {

    public IdentifierCommand(CommonAPI plugin) {
        super(plugin, "identifier", new String[] { });
    }

    @Override
    public String getArguments() {
        return super.getUsage() + " <name>";
    }

    @Override
    public void onCommand(CommandArgs commandArgs) {
        if (commandArgs.length == 1) {
            Player player = commandArgs.getPlayer(0);
            if (player == null) {
                commandArgs.sendMessage("&cNo hay ningun jugador conectado con el nombre &4%s", commandArgs.getString(0));
            } else {
                SimpleIdentifier identifier = getPlugin().getIdentifierManager().getIdentifier(player.getUniqueId());
                if (identifier == null) {
                    commandArgs.sendMessage("&cLa informacion del jugador no ha sido cargada!");
                } else {
                    commandArgs.sendMessage("&7Id: &f" + identifier.getId());
                    commandArgs.sendMessage("&7Nombre: &f" + identifier.getName());
                    commandArgs.sendMessage("&7UUID: &f" + identifier.getUUID().toString());
                }
            }
        } else {
            commandArgs.sendMessage("&cUso incorrect: &4" + getUsage());
        }
    }
}
