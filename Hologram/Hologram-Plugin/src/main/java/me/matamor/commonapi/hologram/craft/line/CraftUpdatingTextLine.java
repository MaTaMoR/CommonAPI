package me.matamor.commonapi.hologram.craft.line;

import lombok.Getter;
import me.matamor.commonapi.hologram.craft.CraftHologram;
import me.matamor.commonapi.hologram.lines.updating.Animation;
import me.matamor.commonapi.hologram.lines.updating.UpdatingHologramLine;

public class CraftUpdatingTextLine extends CraftTextLine implements UpdatingHologramLine {

    private final Animation<String> animation;

    @Getter
    private long delay;

    @Getter
    private long lastUpdateTime;

    public CraftUpdatingTextLine(CraftHologram parent, Animation<String> animation) {
        this(parent, animation, 5000L);
    }

    public CraftUpdatingTextLine(CraftHologram parent, Animation<String> animation, long delay) {
        super(parent, animation.firstSlide());

        this.animation = animation;
        this.delay = delay;
    }

    @Override
    public void update() {
        setText(this.animation.nextSlide());
        this.lastUpdateTime = System.currentTimeMillis();
    }
}
