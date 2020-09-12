package me.matamor.commonapi.inventory.actions.defaults;

import me.matamor.commonapi.inventory.actions.ClickAction;
import org.bukkit.entity.Player;

public class CloseInventory implements ClickAction {

    @Override
    public void execute(Player player) {
        player.closeInventory();
    }
}
