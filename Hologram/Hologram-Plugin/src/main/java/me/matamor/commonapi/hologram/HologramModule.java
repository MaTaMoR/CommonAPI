package me.matamor.commonapi.hologram;

import lombok.Getter;
import me.matamor.commonapi.hologram.task.HologramUpdateTask;
import me.matamor.commonapi.modules.java.JavaModule;
import me.matamor.commonapi.utils.NMSClassLoader;

public class HologramModule extends JavaModule {

    @Getter
    private static HologramModule instance;

    @Getter
    private HologramEntityController entityController;

    @Getter
    private HologramManager hologramManager;

    @Getter
    private Runnable updatingTask;

    @Override
    public void onEnable() {
        if (!setupController()) {
            throw new RuntimeException("Couldn't find a valid HologramEntity controller!");
        }

        this.hologramManager = new SimpleHologramManager();
        this.updatingTask = new HologramUpdateTask(this);

        getServer().getScheduler().runTaskTimer(this, this.updatingTask, 2L, 2L);

        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;

        if (this.hologramManager != null) {
            this.hologramManager.clear();
        }
    }

    private boolean setupController() {
        NMSClassLoader<HologramEntityController> hologramController = new NMSClassLoader<>(this, "me.matamor.commonapi.nms.{version}.hologram.HologramEntityControllerImpl");
        if (hologramController.load()) {
            this.entityController = hologramController.getValue();
        }

        return this.entityController != null;
    }
}