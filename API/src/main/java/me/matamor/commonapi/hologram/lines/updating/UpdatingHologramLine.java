package me.matamor.commonapi.hologram.lines.updating;

import me.matamor.commonapi.hologram.lines.HologramLine;

public interface UpdatingHologramLine extends HologramLine {

    void update();

    long getDelay();

    long getLastUpdateTime();

}
