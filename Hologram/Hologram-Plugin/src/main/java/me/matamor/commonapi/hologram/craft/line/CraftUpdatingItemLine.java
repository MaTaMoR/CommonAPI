package me.matamor.commonapi.hologram.craft.line;

import lombok.Getter;
import me.matamor.commonapi.hologram.craft.CraftHologram;
import me.matamor.commonapi.hologram.lines.updating.Animation;
import me.matamor.commonapi.hologram.lines.updating.UpdatingHologramLine;
import org.bukkit.inventory.ItemStack;

public class CraftUpdatingItemLine extends CraftItemLine implements UpdatingHologramLine {

    private final Animation<ItemStack> animation;

    @Getter
    private long delay;

    @Getter
    private long lastUpdateTime;

    public CraftUpdatingItemLine(CraftHologram parent, Animation<ItemStack> animation) {
        this(parent, animation, 5000L);
    }

    public CraftUpdatingItemLine(CraftHologram parent, Animation<ItemStack> animation, long delay) {
        super(parent, animation.firstSlide());

        this.animation = animation;
        this.delay = delay;
    }

    @Override
    public void update() {
        setItem(this.animation.nextSlide());
        this.lastUpdateTime = System.currentTimeMillis();
    }
}
