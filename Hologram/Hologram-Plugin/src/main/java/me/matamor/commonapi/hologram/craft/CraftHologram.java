package me.matamor.commonapi.hologram.craft;

import lombok.Getter;
import me.matamor.commonapi.hologram.Hologram;
import me.matamor.commonapi.hologram.HologramModule;
import me.matamor.commonapi.hologram.craft.line.*;
import me.matamor.commonapi.hologram.lines.HologramLine;
import me.matamor.commonapi.hologram.lines.ItemLine;
import me.matamor.commonapi.hologram.lines.TextLine;
import me.matamor.commonapi.hologram.lines.updating.Animation;
import me.matamor.commonapi.hologram.lines.updating.UpdatingHologramLine;
import me.matamor.commonapi.utils.SimpleMath;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CraftHologram implements Hologram {

    // Position variables.
    @Getter
    private World world;

    @Getter
    private double x, y, z;
    private int chunkX, chunkZ;

    // The entities that represent lines.
    private final List<CraftHologramLine> lines;

    @Getter
    private boolean deleted;

    public CraftHologram(Location location) {
        Validate.notNull(location, "location");
        updateLocation(location.getWorld(), location.getX(), location.getY(), location.getZ());

        this.lines = new ArrayList<>();
    }

    public boolean isInChunk(Chunk chunk) {
        return chunk.getX() == this.chunkX && chunk.getZ() == this.chunkZ;
    }

    @Override
    public Location getLocation() {
        return new Location(this.world, this.x, this.y, this.z);
    }

    private void updateLocation(World world, double x, double y, double z) {
        Validate.notNull(world, "world");

        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

        this.chunkX = SimpleMath.floor(x) >> 4;
        this.chunkZ = SimpleMath.floor(z) >> 4;
    }

    @Override
    public void delete() {
        if (!this.deleted) {
            this.deleted = true;

            clearLines();
        }
    }

    public List<CraftHologramLine> getLinesUnsafe() {
        return this.lines;
    }

    @Override
    public CraftHologramLine getLine(int index) {
        return this.lines.get(index);
    }

    @Override
    public CraftTextLine addTextLine(String text) {
        Validate.isTrue(!this.deleted, "Can't add lines to a deleted hologram!");

        CraftTextLine line = new CraftTextLine(this, text);

        this.lines.add(line);

        refreshSingleLines();

        return line;
    }

    @Override
    public CraftTextLine addTextLine(int index, String text) {
        Validate.isTrue(!this.deleted, "Can't add lines to a deleted hologram!");

        removeLine(index);

        CraftTextLine line = new CraftTextLine(this, text);
        this.lines.add(index, line);

        refreshSingleLines();

        return line;
    }

    @Override
    public TextLine addUpdatingTextLine(Animation<String> animation) {
        return addUpdatingTextLine(5000L, animation);
    }

    @Override
    public TextLine addUpdatingTextLine(long delay, Animation<String> animation) {
        Validate.isTrue(!this.deleted, "Can't add lines to a deleted hologram!");

        CraftUpdatingTextLine line = new CraftUpdatingTextLine(this, animation, delay);
        this.lines.add(line);

        //Start tracking updates!
        HologramModule.getInstance().getHologramManager().trackLine(line);

        refreshSingleLines();

        return line;
    }

    @Override
    public TextLine addUpdatingTextLine(int index, Animation<String> animation) {
        return addUpdatingTextLine(index, 5000L, animation);
    }

    @Override
    public TextLine addUpdatingTextLine(int index, long delay, Animation<String> animation) {
        Validate.isTrue(!this.deleted, "Can't add lines to a deleted hologram!");

        removeLine(index);

        CraftUpdatingTextLine line = new CraftUpdatingTextLine(this, animation, delay);
        this.lines.add(index, line);

        //Start tracking updates!
        HologramModule.getInstance().getHologramManager().trackLine(line);

        refreshSingleLines();

        return line;
    }

    @Override
    public ItemLine addItemLine(ItemStack item) {
        Validate.isTrue(!this.deleted, "Can't add lines to a deleted hologram!");

        CraftItemLine line = new CraftItemLine(this, item);
        this.lines.add(line);

        refreshSingleLines();

        return line;
    }

    @Override
    public ItemLine addItemLine(int index, ItemStack item) {
        Validate.isTrue(!this.deleted, "Can't add lines to a deleted hologram!");

        removeLine(index);

        CraftItemLine line = new CraftItemLine(this, item);
        this.lines.add(index, line);

        refreshSingleLines();

        return line;
    }

    @Override
    public ItemLine addUpdatingItemLine(Animation<ItemStack> animation) {
        return addUpdatingItemLine(5000L, animation);
    }

    @Override
    public ItemLine addUpdatingItemLine(long delay, Animation<ItemStack> animation) {
        Validate.isTrue(!this.deleted, "Can't add lines to a deleted hologram!");

        CraftUpdatingItemLine line = new CraftUpdatingItemLine(this, animation, delay);
        this.lines.add(line);

        //Start tracking updates!
        HologramModule.getInstance().getHologramManager().trackLine(line);

        refreshSingleLines();

        return line;
    }

    @Override
    public ItemLine addUpdatingItemLine(int index, Animation<ItemStack> animation) {
        return addUpdatingItemLine(index, 5000L, animation);
    }

    @Override
    public ItemLine addUpdatingItemLine(int index, long delay, Animation<ItemStack> animation) {
        Validate.isTrue(!this.deleted, "Can't add lines to a deleted hologram!");

        removeLine(index);

        CraftUpdatingItemLine line = new CraftUpdatingItemLine(this, animation);
        this.lines.add(index, line);

        //Start tracking updates!
        HologramModule.getInstance().getHologramManager().trackLine(line);

        refreshSingleLines();

        return line;
    }

    @Override
    public void removeLine(int index) {
        Validate.isTrue(!this.deleted, "hologram already deleted");

        CraftHologramLine line = this.lines.remove(index);

        if (line != null) {
            if (line instanceof UpdatingHologramLine) {
                HologramModule.getInstance().getHologramManager().untrackLine((UpdatingHologramLine) line);
            }

            line.despawn();

            refreshSingleLines();
        }
    }

    public void removeLine(CraftHologramLine line) {
        Validate.isTrue(!this.deleted, "hologram already deleted");

        if (this.lines.remove(line)) {
            if (line instanceof UpdatingHologramLine) {
                HologramModule.getInstance().getHologramManager().untrackLine((UpdatingHologramLine) line);
            }

            line.despawn();

            refreshSingleLines();
        }
    }

    @Override
    public int size() {
        return this.lines.size();
    }

    @Override
    public double getHeight() {
        if (this.lines.isEmpty()) {
            return 0;
        }

        double height = 0.0;

        for (CraftHologramLine line : this.lines) {
            height += line.getHeight();
        }

        height += HologramLine.SPACE_BETWEEN_LINES * (this.lines.size() - 1);

        return height;
    }

    @Override
    public void hide() {
        this.lines.forEach(CraftHologramLine::despawn);
    }

    @Override
    public void clearLines() {
        for (CraftHologramLine line : this.lines) {
            line.despawn();
        }

        this.lines.clear();
    }

    public void refreshAll() {
        if (this.world.isChunkLoaded(this.chunkX, this.chunkZ)) {
            spawnEntities();
        }
    }

    public void refreshSingleLines() {
        if (this.world.isChunkLoaded(this.chunkX, this.chunkZ)) {
            double currentY = this.y;
            boolean first = true;

            for (CraftHologramLine line : this.lines) {

                currentY -= line.getHeight();

                if (first) {
                    first = false;
                } else {
                    currentY -= HologramLine.SPACE_BETWEEN_LINES;
                }

                if (line.isSpawned()) {
                    line.teleport(this.x, currentY, this.z);
                } else {
                    line.spawn(this.world, this.x, currentY, this.z);
                }
            }
        }
    }

    public void spawnEntities() {
        Validate.isTrue(!this.deleted, "hologram already deleted");

        despawnEntities();

        double currentY = this.y;
        boolean first = true;

        for (CraftHologramLine line : this.lines) {

            currentY -= line.getHeight();

            if (first) {
                first = false;
            } else {
                currentY -= HologramLine.SPACE_BETWEEN_LINES;
            }

            line.spawn(this.world, this.x, currentY, this.z);
        }
    }

    public void despawnEntities() {
        for (CraftHologramLine piece : this.lines) {
            piece.despawn();
        }
    }

    @Override
    public void teleport(Location location) {
        Validate.notNull(location, "location");

        teleport(location.getWorld(), location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void teleport(World world, double x, double y, double z) {
        Validate.isTrue(!this.deleted, "hologram already deleted");
        Validate.notNull(world, "world");

        updateLocation(world, x, y, z);

        if (this.world != world) {
            despawnEntities();
            refreshAll();
            return;
        }

        double currentY = y;
        boolean first = true;

        for (CraftHologramLine line : this.lines) {

            if (!line.isSpawned()) {
                continue;
            }

            currentY -= line.getHeight();

            if (first) {
                first = false;
            } else {
                currentY -= HologramLine.SPACE_BETWEEN_LINES;
            }

            line.teleport(x, currentY, z);
        }
    }

    @Override
    public String toString() {
        return "CraftHologram [world=" + this.world + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", lines=" + this.lines + ", deleted=" + this.deleted + "]";
    }
}