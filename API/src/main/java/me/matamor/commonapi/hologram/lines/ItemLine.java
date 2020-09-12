package me.matamor.commonapi.hologram.lines;

import org.bukkit.inventory.ItemStack;

public interface ItemLine extends HologramLine {

    void setItem(ItemStack item);

    ItemStack getItem();

}
