package me.matamor.commonapi.nms.v1_8_R3.hologram;

import me.matamor.commonapi.hologram.lines.HologramLine;
import me.matamor.commonapi.hologram.nms.NMSArmorStand;
import me.matamor.commonapi.utils.Reflections;
import me.matamor.commonapi.utils.SimpleMath;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class EntityNMSArmorStand extends EntityArmorStand implements NMSArmorStand {

    private static final Reflections.FieldAccessor<Integer> DISABLED_SLOTS_FIELD = Reflections.getField(EntityArmorStand.class, "bi", int.class);
    private static final Reflections.MethodInvoker SET_MARKER_METHOD = Reflections.getMethod(EntityArmorStand.class, "n", boolean.class);

    private boolean lockTick;
    private HologramLine parentPiece;

    public EntityNMSArmorStand(World world, HologramLine parentPiece) {
        super(world);

        setInvisible(true);
        setSmall(true);
        setArms(false);
        setGravity(true);
        setBasePlate(true);

        try {
            SET_MARKER_METHOD.invoke(this, true);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        this.parentPiece = parentPiece;

        try {
            DISABLED_SLOTS_FIELD.set(this, Integer.MAX_VALUE);
        } catch (Exception e) {
            // There's still the overridden method.
        }

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
    public void setCustomName(String customName) {
        // Locks the custom name.
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        // Locks the custom name.
    }

    @Override
    public boolean a(EntityHuman human, Vec3D vec3d) {
        // Prevent stand being equipped
        return true;
    }

    @Override
    public boolean d(int i, ItemStack item) {
        // Prevent stand being equipped
        return false;
    }

    @Override
    public void setEquipment(int i, ItemStack item) {
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
    public void t_() {
        if (!lockTick) {
            super.t_();
        }
    }

    @Override
    public void makeSound(String sound, float f1, float f2) {
        // Remove sounds.
    }

    @Override
    public void setCustomNameNMS(String name) {
        if (name != null && name.length() > 300) {
            name = name.substring(0, 300);
        }

        if (name == null) {
            name = "";
        }

        super.setCustomName(name);
        super.setCustomNameVisible(!name.isEmpty());
    }

    @Override
    public String getCustomNameNMS() {
        return super.getCustomName();
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
    public CraftEntity getBukkitEntity() {
        if (super.bukkitEntity == null) {
            this.bukkitEntity = new CraftNMSArmorStand(this.world.getServer(), this);
        }

        return this.bukkitEntity;
    }

    @Override
    public void killEntityNMS() {
        die();
    }

    @Override
    public void setLocationNMS(double x, double y, double z) {
        super.setPosition(x, y, z);

        // Send a packet near to update the position.
        PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(
                getIdNMS(),
                MathHelper.floor(this.locX * 32.0D),
                MathHelper.floor(this.locY * 32.0D),
                MathHelper.floor(this.locZ * 32.0D),
                (byte) (int) (this.yaw * 256.0F / 360.0F),
                (byte) (int) (this.pitch * 256.0F / 360.0F),
                this.onGround
        );

        for (Object obj : this.world.players) {
            if (obj instanceof EntityPlayer) {
                EntityPlayer nmsPlayer = (EntityPlayer) obj;

                double distanceSquared = SimpleMath.square(nmsPlayer.locX - this.locX) + SimpleMath.square(nmsPlayer.locZ - this.locZ);
                if (distanceSquared < 8192 && nmsPlayer.playerConnection != null) {
                    nmsPlayer.playerConnection.sendPacket(teleportPacket);
                }
            }
        }
    }

    @Override
    public boolean isDeadNMS() {
        return this.dead;
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