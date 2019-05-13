package me.matamor.commonapi.custominventories.actions;

import me.matamor.commonapi.custominventories.enums.ClickType;
import me.matamor.commonapi.custominventories.inventories.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class AdvancedClickAction implements ActionHandler {

    public abstract void execute(Player player, ClickType clickType, boolean shift);

    @Override
    public void handle(CustomInventory customInventory, Player player, ClickType clickType, boolean shift, InventoryClickEvent event) {
        execute(player, clickType, shift);
    }
}
