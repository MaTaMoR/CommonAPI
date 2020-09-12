package me.matamor.commonapi.hologram.craft.line;

import lombok.Getter;
import me.matamor.commonapi.hologram.HologramModule;
import me.matamor.commonapi.hologram.craft.CraftHologram;
import me.matamor.commonapi.hologram.lines.ItemLine;
import me.matamor.commonapi.hologram.nms.NMSEntityBase;
import me.matamor.commonapi.hologram.nms.NMSItem;
import me.matamor.commonapi.nms.Offsets;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class CraftItemLine extends CraftHologramLine implements ItemLine {

    @Getter
    private ItemStack item;

    @Getter
    private NMSItem nmsItem;

    private NMSEntityBase nmsVehicle;

    public CraftItemLine(CraftHologram parent, ItemStack item) {
        super(0.7, parent);

        this.item = item;
    }

    @Override
    public void spawn(World world, double x, double y, double z) {
        super.spawn(world, x, y, z);

        if (this.item != null) {
            double offset = getItemOffset();

            this.nmsItem = HologramModule.getInstance().getEntityController().spawnNMSItem(this, this.item, world, x, y + offset, z);
            this.nmsVehicle = HologramModule.getInstance().getEntityController().spawnNMSArmorStand(this, world, x, y + offset, z);

            this.nmsItem.setMountNMS(this.nmsVehicle);

            this.nmsItem.setLockTick(true);
            this.nmsVehicle.setLockTick(true);
        }
    }

    @Override
    public void despawn() {
        super.despawn();

        if (this.nmsVehicle != null) {
            this.nmsVehicle.killEntityNMS();
            this.nmsVehicle = null;
        }

        if (this.nmsItem != null) {
            this.nmsItem.killEntityNMS();
            this.nmsItem = null;
        }
    }

    @Override
    public void teleport(double x, double y, double z) {
        double offset = getItemOffset();

        if (this.nmsVehicle != null) {
            this.nmsVehicle.setLocationNMS(x, y + offset, z);
        }

        if (this.nmsItem != null) {
            this.nmsItem.setLocationNMS(x, y + offset, z);
        }
    }

    @Override
    public void setItem(ItemStack item) {
        Validate.notNull(item, "ItemStack can't be null!");

        if (this.nmsItem != null) {
            this.item = item;
            this.nmsItem.setItemNMS(this.item);
        }
    }

    @Override
    public boolean isHidden() {
        return this.nmsItem == null;
    }

    private double getItemOffset() {
        return Offsets.ARMOR_STAND_WITH_ITEM;
    }

}
