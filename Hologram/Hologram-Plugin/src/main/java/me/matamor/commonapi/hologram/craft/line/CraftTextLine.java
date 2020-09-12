package me.matamor.commonapi.hologram.craft.line;

import lombok.Getter;
import me.matamor.commonapi.hologram.HologramModule;
import me.matamor.commonapi.hologram.craft.CraftHologram;
import me.matamor.commonapi.hologram.lines.TextLine;
import me.matamor.commonapi.hologram.nms.NMSNameable;
import me.matamor.commonapi.nms.Offsets;
import me.matamor.commonapi.utils.StringUtils;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.World;

import java.util.Objects;

public class CraftTextLine extends CraftHologramLine implements TextLine {

    @Getter
    private String text;

    @Getter
    private NMSNameable nameable;

    public CraftTextLine(CraftHologram parent, String text) {
        super(0.26, parent);

        setText(text);
    }

    @Override
    public void spawn(World world, double x, double y, double z) {
        super.spawn(world, x, y, z);

        this.nameable = HologramModule.getInstance().getEntityController().spawnNMSArmorStand(this, world, x, y + getTextOffset(), z);

        if (this.text != null && !this.text.isEmpty()) {
            this.nameable.setCustomNameNMS(StringUtils.color(this.text));
        }
    }

    @Override
    public void despawn() {
        super.despawn();

        if (this.nameable != null) {
            this.nameable.killEntityNMS();
            this.nameable = null;
        }
    }

    @Override
    public void teleport(double x, double y, double z) {
        if (this.nameable != null) {
            this.nameable.setLocationNMS(x, y + getTextOffset(), z);
        }
    }

    @Override
    public void setText(String text) {
        Validate.notNull(text, "Text can't be null!");

        if (!Objects.equals(this.text, text)) {
            this.text = text;

            if (this.nameable != null) {
                this.nameable.setCustomNameNMS(StringUtils.color(this.text));
            }
        }
    }

    @Override
    public boolean isHidden() {
        return this.nameable == null;
    }

    private double getTextOffset() {
        return Offsets.ARMOR_STAND_ALONE;
    }
}
