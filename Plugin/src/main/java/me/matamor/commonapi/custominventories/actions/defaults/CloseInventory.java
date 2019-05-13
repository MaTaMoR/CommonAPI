package me.matamor.commonapi.custominventories.actions.defaults;

import me.matamor.commonapi.custominventories.actions.ClickAction;
import org.bukkit.entity.Player;

public class CloseInventory implements ClickAction {

    @Override
    public void execute(Player player) {
        player.closeInventory();
    }
}
