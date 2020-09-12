package me.matamor.commonapi.hologram;

import me.matamor.commonapi.hologram.lines.HologramLine;
import me.matamor.commonapi.hologram.nms.NMSArmorStand;
import me.matamor.commonapi.hologram.nms.NMSItem;
import me.matamor.commonapi.nms.NMSController;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public interface HologramEntityController extends NMSController {

    NMSArmorStand spawnNMSArmorStand(HologramLine line, World world, double x, double y, double z);

    NMSItem spawnNMSItem(HologramLine line, ItemStack item, World world, double x, double y, double z);

}
