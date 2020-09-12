package me.matamor.commonapi.hologram.craft.line;

import lombok.Getter;
import me.matamor.commonapi.hologram.craft.CraftHologram;
import me.matamor.commonapi.hologram.lines.HologramLine;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.World;

public abstract class CraftHologramLine implements HologramLine {

    @Getter
    private final double height;

    @Getter
    private final CraftHologram parent;

    @Getter
    private boolean spawned;

    protected CraftHologramLine(double height, CraftHologram parent) {
        Validate.notNull(parent, "parent hologram");

        this.height = height;
        this.parent = parent;
    }

    public void removeLine() {
        this.parent.removeLine(this);
    }

    public void spawn(World world, double x, double y, double z) {
        Validate.notNull(world, "world");

        // Remove the old entities when spawning the new ones.
        despawn();

        this.spawned = true;
    }

    public void despawn() {
        this.spawned = false;
    }

    public abstract void teleport(double x, double y, double z);
}
