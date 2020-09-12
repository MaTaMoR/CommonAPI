package me.matamor.commonapi.hologram;

import me.matamor.commonapi.hologram.lines.updating.UpdatingHologramLine;
import org.bukkit.Location;

import java.util.Collection;

public interface HologramManager {

    Hologram createHologram(Location location);

    void deleteHologram(Hologram hologram);

    Collection<Hologram> getActiveHolograms();

    void addActiveHologram(Hologram hologram);

    void removeActiveHologram(Hologram hologram);

    void clear();

    void trackLine(UpdatingHologramLine line);

    boolean untrackLine(UpdatingHologramLine line);

    Collection<? extends UpdatingHologramLine> getTrackedLines();

}
