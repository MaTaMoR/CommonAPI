package me.matamor.commonapi.custominventories.actions;

import me.matamor.commonapi.custominventories.enums.ClickType;
import me.matamor.commonapi.custominventories.inventories.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ClickAction extends ActionHandler {

    void execute(Player player);

    @Override
    default void handle(CustomInventory customInventory, Player player, ClickType clickType, boolean shift, InventoryClickEvent event) {
        execute(player);
    }
}
