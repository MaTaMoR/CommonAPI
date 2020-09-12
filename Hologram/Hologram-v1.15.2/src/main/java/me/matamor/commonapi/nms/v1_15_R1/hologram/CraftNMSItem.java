package me.matamor.commonapi.nms.v1_15_R1.hologram;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CraftNMSItem extends CraftItem {

    public CraftNMSItem(CraftServer server, EntityNMSItem entity) {
        super(server, entity);
    }

    // Disallow all the bukkit methods.

    @Override
    public void remove() {
        // Cannot be removed, this is the most important to override.
    }

    // Methods from Item class
    @Override public void setItemStack(ItemStack stack) { }
    @Override public void setPickupDelay(int delay) { }

    // Methods from Entity class
    @Override public void setVelocity(Vector vel) { }
    @Override public boolean teleport(Location loc) { return false; }
    @Override public boolean teleport(Entity entity) { return false; }
    @Override public boolean teleport(Location loc, PlayerTeleportEvent.TeleportCause cause) { return false; }
    @Override public boolean teleport(Entity entity, PlayerTeleportEvent.TeleportCause cause) { return false; }
    @Override public void setFireTicks(int ticks) { }
    @Override public boolean setPassenger(Entity entity) { return false; }
    @Override public boolean eject() { return false; }
    @Override public boolean leaveVehicle() { return false; }
    @Override public void playEffect(EntityEffect effect) { }
    @Override public void setCustomName(String name) { }
    @Override public void setCustomNameVisible(boolean flag) { }
    @Override public void setGlowing(boolean flag) { }
    @Override public void setGravity(boolean gravity) { }
    @Override public void setInvulnerable(boolean flag) { }
    @Override public void setMomentum(Vector value) { }
    @Override public void setSilent(boolean flag) { }
    @Override public void setTicksLived(int value) { }
    @Override public void setPersistent(boolean flag) { }
    @Override public void setRotation(float yaw, float pitch) { }

}