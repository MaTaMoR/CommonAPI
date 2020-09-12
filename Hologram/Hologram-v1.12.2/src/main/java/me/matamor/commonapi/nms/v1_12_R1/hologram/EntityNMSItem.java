package me.matamor.commonapi.nms.v1_12_R1.hologram;

import me.matamor.commonapi.hologram.lines.HologramLine;
import me.matamor.commonapi.hologram.nms.NMSEntityBase;
import me.matamor.commonapi.hologram.nms.NMSItem;
import me.matamor.commonapi.utils.ItemUtils;
import me.matamor.commonapi.utils.Reflections;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

public class EntityNMSItem extends EntityItem implements NMSItem {

    private static final Reflections.FieldAccessor<Entity> VEHICLE_FIELD = Reflections.getField(Entity.class, "au", Entity.class);

    private boolean lockTick;
    private HologramLine line;

    public EntityNMSItem(World world, HologramLine line) {
        super(world);

        this.line = line;
        this.noclip = true;

        super.pickupDelay = 32767; // Lock the item pickup delay, also prevents entities from picking up the item
    }

    @Override
    public void B_() {
        // So it won't get removed.
        ticksLived = 0;

        if (!lockTick) {
            super.B_();
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
    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return nbttagcompound;
    }

    @Override
    public void f(NBTTagCompound nbttagcompound) {
        // Do not load NBT.
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        // Do not load NBT.
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
    public boolean isCollidable() {
        return false;
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
        // Prevent being killed.
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
            super.bukkitEntity = new CraftNMSItem(super.world.getServer(), this);
        }

        return super.bukkitEntity;
    }

    @Override
    public boolean isDeadNMS() {
        return super.dead;
    }

    @Override
    public void killEntityNMS() {
        super.dead = true;
    }

    @Override
    public void setLocationNMS(double x, double y, double z) {
        super.setPosition(x, y, z);
    }

    @Override
    public void setItemNMS(org.bukkit.inventory.ItemStack stack) {
        ItemStack newItem = CraftItemStack.asNMSCopy(stack); // ItemStack.a is returned if the stack is null, invalid or the material is not an Item

        if (newItem == null || newItem == ItemStack.a) {
            newItem = new ItemStack(Blocks.BEDROCK);
        }

        if (newItem.getTag() == null) {
            newItem.setTag(new NBTTagCompound());
        }

        NBTTagCompound display = newItem.getTag().getCompound("display"); // Returns a new NBTTagCompound if not existing
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
        return super.getId();
    }

    @Override
    public HologramLine getHologramLine() {
        return line;
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
        Entity oldVehicle = super.bJ();
        if (oldVehicle != null) {
            VEHICLE_FIELD.set(this, null);
            oldVehicle.passengers.remove(this);
        }

        VEHICLE_FIELD.set(this, entity);

        entity.passengers.clear();
        entity.passengers.add(this);
    }
}