package me.matamor.commonapi.hologram.nms;

import me.matamor.commonapi.hologram.lines.HologramLine;
import org.bukkit.inventory.ItemStack;

public interface NMSItem extends NMSMountable {

    HologramLine getHologramLine();

    // Sets the item to the entity
    void setItemNMS(ItemStack itemStack);


}
