package me.matamor.commonapi.hologram.lines;

import me.matamor.commonapi.hologram.Hologram;

public interface HologramLine {

    double SPACE_BETWEEN_LINES = 0.02;

    Hologram getParent();

    boolean isHidden();

    void removeLine();

    double getHeight();

}
