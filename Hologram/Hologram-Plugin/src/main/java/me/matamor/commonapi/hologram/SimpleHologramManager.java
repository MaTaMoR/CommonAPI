package me.matamor.commonapi.hologram;

import lombok.RequiredArgsConstructor;
import me.matamor.commonapi.hologram.craft.CraftHologram;
import me.matamor.commonapi.hologram.lines.updating.UpdatingHologramLine;
import org.bukkit.Location;

import java.util.*;

@RequiredArgsConstructor
public class SimpleHologramManager implements HologramManager {

    private final List<Hologram> activeHolograms = new ArrayList<>();

    private Set<UpdatingHologramLine> trackedUpdatingLines = new HashSet<>();

    @Override
    public Hologram createHologram(Location location) {
        Hologram hologram = new CraftHologram(location);

        this.activeHolograms.add(hologram);

        return hologram;
    }

    @Override
    public void deleteHologram(Hologram hologram) {
        hologram.delete();

        removeActiveHologram(hologram);
    }

    @Override
    public Collection<Hologram> getActiveHolograms() {
        return this.activeHolograms;
    }

    @Override
    public void addActiveHologram(Hologram hologram) {
        this.activeHolograms.add(hologram);
    }

    @Override
    public void removeActiveHologram(Hologram hologram) {
        this.activeHolograms.remove(hologram);
    }

    @Override
    public void clear() {
        this.activeHolograms.forEach(Hologram::delete);
        this.activeHolograms.clear();

        this.trackedUpdatingLines.clear();
    }

    @Override
    public void trackLine(UpdatingHologramLine line) {
        this.trackedUpdatingLines.add(line);
    }

    @Override
    public boolean untrackLine(UpdatingHologramLine line) {
        return this.trackedUpdatingLines.remove(line);
    }

    @Override
    public Collection<? extends UpdatingHologramLine> getTrackedLines() {
        return this.trackedUpdatingLines;
    }
}