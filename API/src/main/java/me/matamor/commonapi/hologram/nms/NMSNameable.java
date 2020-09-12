package me.matamor.commonapi.hologram.nms;

import me.matamor.commonapi.hologram.lines.HologramLine;

public interface NMSNameable extends NMSEntityBase {

    HologramLine getHologramLine();

    // Sets a custom name for this entity.
    void setCustomNameNMS(String name);

    // Returns the custom name of this entity.
    String getCustomNameNMS();

}
