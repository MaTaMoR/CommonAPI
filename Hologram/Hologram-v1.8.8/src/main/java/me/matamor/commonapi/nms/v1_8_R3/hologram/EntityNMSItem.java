package me.matamor.commonapi.nms.v1_8_R3.hologram;

import me.matamor.commonapi.hologram.lines.HologramLine;
import me.matamor.commonapi.hologram.nms.NMSEntityBase;
import me.matamor.commonapi.hologram.nms.NMSItem;
import me.matamor.commonapi.utils.ItemUtils;
import me.matamor.commonapi.utils.Reflections;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class EntityNMSItem extends EntityItem implements NMSItem {

    private static final Reflections.FieldAccessor<Double> RIDER_PITCH_DELTA = Reflections.getField(Entity.class, "ar", double.class);
    private static final Reflections.FieldAccessor<Double> RIDER_YAW_DELTA = Reflections.getField(Entity.class, "as", double.class);

    private boolean lockTick;
    private HologramLine parentPiece;

    public EntityNMSItem(World world, HologramLine piece) {
        super(world);

        super.pickupDelay = Integer.MAX_VALUE;
        this.parentPiece = piece;
    }

    @Override
    public void t_() {

        // So it won't get removed.
        ticksLived = 0;

        if (!lockTick) {
            super.t_();
        }
    }

    // Method called when a player is near.
    @Override
    public void d(EntityHuman human) {

    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
    }

    @Override
    public boolean c(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return false;
    }

    @Override
    public boolean d(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return false;
    }

    @Override
    public void e(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
    }

    @Override
    public boolean isInvulnerable(DamageSource source) {
        /*
         * The field Entity.invulnerable is private.
         * It's only used while saving NBTTags, but since the entity would be killed
         * on chunk unload, we prefer to override isInvulnerable().
         */
        return true;
    }

    @Override
    public void inactiveTick() {
        // Check inactive ticks.

        if (!lockTick) {
            super.inactiveTick();
        }
    }

    @Override
    public void setLockTick(boolean lock) {
        lockTick = lock;
    }

    @Override
    public void die() {
        setLockTick(false);
        super.die();
    }

    @Override
    public boolean isAlive() {
        // This override prevents items from being picked up by hoppers.
        // Should have no side effects.
        return false;
    }

    @Override
    public CraftEntity getBukkitEntity() {
        if (super.bukkitEntity == null) {
            this.bukkitEntity = new CraftNMSItem(this.world.getServer(), this);
        }

        return this.bukkitEntity;
    }

    @Override
    public boolean isDeadNMS() {
        return this.dead;
    }

    @Override
    public void killEntityNMS() {
        die();
    }

    @Override
    public void setLocationNMS(double x, double y, double z) {
        super.setPosition(x, y, z);
    }

    @Override
    public void setItemNMS(org.bukkit.inventory.ItemStack stack) {
        ItemStack newItem = CraftItemStack.asNMSCopy(stack);

        if (newItem == null) {
            newItem = new ItemStack(Blocks.BEDROCK);
        }

        if (newItem.getTag() == null) {
            newItem.setTag(new NBTTagCompound());
        }
        NBTTagCompound display = newItem.getTag().getCompound("display");

        if (!newItem.getTag().hasKey("display")) {
            newItem.getTag().set("display", display);
        }

        NBTTagList tagList = new NBTTagList();
        tagList.add(new NBTTagString(ItemUtils.ANTISTACK_LORE)); // Antistack lore
        display.set("Lore", tagList);

        setItemStack(newItem);
    }

    @Override
    public int getIdNMS() {
        return this.getId();
    }

    @Override
    public HologramLine getHologramLine() {
        return parentPiece;
    }

    @Override
    public org.bukkit.entity.Entity getBukkitEntityNMS() {
        return getBukkitEntity();
    }

    @Override
    public void setMountNMS(NMSEntityBase vehicleBase) {
        if (!(vehicleBase instanceof Entity)) {
            // It should never dismount
            return;
        }

        Entity entity = (Entity) vehicleBase;

        RIDER_PITCH_DELTA.set(this, 0.0);
        RIDER_YAW_DELTA.set(this, 0.0);

        if (this.vehicle != null) {
            this.vehicle.passenger = null;
        }

        this.vehicle = entity;
        entity.passenger = this;
    }
}