package me.matamor.commonapi.inventory.actions;

import me.matamor.commonapi.inventory.enums.ClickType;
import me.matamor.commonapi.inventory.inventories.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ActionHandler {

    void handle(CustomInventory customInventory, Player player, ClickType clickType, boolean shift, InventoryClickEvent event);

}
