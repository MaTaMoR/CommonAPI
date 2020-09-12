package me.matamor.commonapi.hologram.task;

import me.matamor.commonapi.hologram.HologramManager;
import me.matamor.commonapi.hologram.HologramModule;
import me.matamor.commonapi.hologram.lines.updating.UpdatingHologramLine;

public class HologramUpdateTask implements Runnable {

    public HologramModule plugin;

    public HologramUpdateTask(HologramModule plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        HologramManager hologramManager = this.plugin.getHologramManager();
        long time = System.currentTimeMillis();

        // Update all lines
        hologramManager.getTrackedLines().stream()
                .filter(line -> !line.isHidden()) // Don't update hidden lines
                .filter(line -> time > line.getLastUpdateTime() + line.getDelay()) // Allow intervals set by implementation
                .forEach(UpdatingHologramLine::update);
    }
}
