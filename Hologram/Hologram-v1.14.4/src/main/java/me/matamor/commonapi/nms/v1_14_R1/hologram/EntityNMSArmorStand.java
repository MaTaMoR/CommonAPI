package me.matamor.commonapi.nms.v1_14_R1.hologram;

import me.matamor.commonapi.hologram.lines.HologramLine;
import me.matamor.commonapi.hologram.nms.NMSArmorStand;
import me.matamor.commonapi.utils.SimpleMath;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftChatMessage;

public class EntityNMSArmorStand extends EntityArmorStand implements NMSArmorStand {

    private boolean lockTick;
    private HologramLine parentPiece;
    private CraftEntity customBukkitEntity;

    public EntityNMSArmorStand(World world, HologramLine parentPiece) {
        super(EntityTypes.ARMOR_STAND, world);
        super.setInvisible(true);
        super.setSmall(true);
        super.setArms(false);
        super.setNoGravity(true);
        super.setBasePlate(true);
        super.setMarker(true);
        super.collides = false;
        this.parentPiece = parentPiece;
        forceSetBoundingBox(new NullBoundingBox());
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
    public void setCustomName(IChatBaseComponent ichatbasecomponent) {
        // Locks the custom name.
    }

    @Override
    public void inactiveTick() {
        // Check inactive ticks.

        if (!lockTick) {
            super.inactiveTick();
        }
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        // Locks the custom name.
    }

    @Override
    public EnumInteractionResult a(EntityHuman human, Vec3D vec3d, EnumHand enumhand) {
        // Prevent stand being equipped
        return EnumInteractionResult.PASS;
    }

    @Override
    public boolean a_(int i, ItemStack item) {
        // Prevent stand being equipped
        return false;
    }

    @Override
    public void setSlot(EnumItemSlot enumitemslot, ItemStack itemstack) {
        // Prevent stand being equipped
    }

    @Override
    public void a(AxisAlignedBB boundingBox) {
        // Do not change it!
    }

    public void forceSetBoundingBox(AxisAlignedBB boundingBox) {
        super.a(boundingBox);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void tick() {
        if (!lockTick) {
            super.tick();
        }
    }

    @Override
    public void a(SoundEffect soundeffect, float f, float f1) {
        // Remove sounds.
    }

    @Override
    public void setCustomNameNMS(String name) {
        if (name != null && name.length() > 300) {
            name = name.substring(0, 300);
        }

        super.setCustomName(CraftChatMessage.fromStringOrNull(name));
        super.setCustomNameVisible(name != null && !name.isEmpty());
    }

    @Override
    public String getCustomNameNMS() {
        return CraftChatMessage.fromComponent(super.getCustomName());
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
    public CraftEntity getBukkitEntity() {
        if (customBukkitEntity == null) {
            customBukkitEntity = new CraftNMSArmorStand(super.world.getServer(), this);
        }
        return customBukkitEntity;
    }

    @Override
    public void killEntityNMS() {
        super.dead = true;
    }

    @Override
    public void setLocationNMS(double x, double y, double z) {
        super.setPosition(x, y, z);

        // Send a packet near to update the position.
        PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(this);

        for (Object obj : super.world.getPlayers()) {
            if (obj instanceof EntityPlayer) {
                EntityPlayer nmsPlayer = (EntityPlayer) obj;

                double distanceSquared = SimpleMath.square(nmsPlayer.locX - super.locX) + SimpleMath.square(nmsPlayer.locZ - super.locZ);
                if (distanceSquared < 8192 && nmsPlayer.playerConnection != null) {
                    nmsPlayer.playerConnection.sendPacket(teleportPacket);
                }
            }
        }
    }

    @Override
    public boolean isDeadNMS() {
        return super.dead;
    }

    @Override
    public int getIdNMS() {
        return super.getId(); // Return the real ID without checking the stack trace.
    }

    @Override
    public HologramLine getHologramLine() {
        return parentPiece;
    }

    @Override
    public org.bukkit.entity.Entity getBukkitEntityNMS() {
        return getBukkitEntity();
    }
}