package me.matamor.commonapi.nms.v1_13_R2.hologram;

import me.matamor.commonapi.hologram.HologramEntityController;
import me.matamor.commonapi.hologram.lines.HologramLine;
import me.matamor.commonapi.hologram.nms.NMSArmorStand;
import me.matamor.commonapi.hologram.nms.NMSItem;
import me.matamor.commonapi.nms.NMSVersion;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class HologramEntityControllerImpl implements HologramEntityController {

    private Plugin plugin;

    @Override
    public void initialize(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public NMSVersion getVersion() {
        return NMSVersion.v1_13_R2;
    }

    @Override
    public NMSArmorStand spawnNMSArmorStand(HologramLine line, World world, double x, double y, double z) {
        WorldServer nmsWorld = ((CraftWorld) world).getHandle();

        EntityNMSArmorStand armorStand = new EntityNMSArmorStand(nmsWorld, line);
        armorStand.setPosition(x, y, z);

        if (!addEntityToWorld(nmsWorld, armorStand)) {
            this.plugin.getLogger().log(Level.WARNING, "Failed to spawn hologram entity in world " + world.getName() + " at x:" + x + " y:" + y + " z:" + z);

            armorStand.killEntityNMS();
            return null;
        }

        return armorStand;
    }

    @Override
    public NMSItem spawnNMSItem(HologramLine line, ItemStack item, World world, double x, double y, double z) {
        WorldServer nmsWorld = ((CraftWorld) world).getHandle();
        EntityNMSItem customItem = new EntityNMSItem(nmsWorld, line);
        customItem.setLocationNMS(x, y, z);
        customItem.setItemNMS(item);

        if (!addEntityToWorld(nmsWorld, customItem)) {
            this.plugin.getLogger().log(Level.WARNING, "Failed to spawn hologram entity in world " + world.getName() + " at x:" + x + " y:" + y + " z:" + z);

            customItem.killEntityNMS();
            return null;
        }

        return customItem;
    }

    private boolean addEntityToWorld(WorldServer nmsWorld, Entity nmsEntity) {
        net.minecraft.server.v1_13_R2.Chunk nmsChunk = nmsWorld.getChunkAtWorldCoords(nmsEntity.getChunkCoordinates());

        if (nmsChunk != null) {
            Chunk chunk = nmsChunk.bukkitChunk;

            if (!chunk.isLoaded()) {
                chunk.load();

                this.plugin.getLogger().info("Loaded chunk (x:" + chunk.getX() + " z:" + chunk.getZ() + ") to spawn a Hologram");
            }
        }

        return nmsWorld.addEntity(nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }
}
