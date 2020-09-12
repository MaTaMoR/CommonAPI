package me.matamor.commonapi.inventory.actions;

import me.matamor.commonapi.inventory.enums.ClickType;
import me.matamor.commonapi.inventory.inventories.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class AdvancedClickAction implements ActionHandler {

    public abstract void execute(Player player, ClickType clickType, boolean shift);

    @Override
    public void handle(CustomInventory customInventory, Player player, ClickType clickType, boolean shift, InventoryClickEvent event) {
        execute(player, clickType, shift);
    }
}
