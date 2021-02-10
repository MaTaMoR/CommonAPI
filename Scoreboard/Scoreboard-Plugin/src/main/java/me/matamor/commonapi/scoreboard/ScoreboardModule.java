package me.matamor.commonapi.scoreboard;

import lombok.Getter;
import me.matamor.commonapi.modules.java.JavaModule;
import me.matamor.commonapi.scoreboard.board.CraftScoreboardManager;
import me.matamor.commonapi.utils.NMSClassLoader;

public class ScoreboardModule extends JavaModule {

    @Getter
    private static ScoreboardModule instance;

    @Getter
    private ScoreboardController scoreboardController;

    @Getter
    private CraftScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        if (!setupController()) {
            throw new RuntimeException("Couldn't find a valid Scoreboard controller!");
        }

        instance = this;

        this.scoreboardManager = new CraftScoreboardManager(this);
        this.scoreboardManager.getTaskHandler().start();
    }

    @Override
    public void onDisable() {
        instance = null;

        if (this.scoreboardManager != null) {
            this.scoreboardManager.clear();
        }
    }

    private boolean setupController() {
        NMSClassLoader<ScoreboardController> hologramController = new NMSClassLoader<>(this, "me.matamor.commonapi.nms.{version}.scoreboard.ScoreboardControllerImpl");
        if (hologramController.load()) {
            this.scoreboardController = hologramController.getValue();
        }

        return this.scoreboardController != null;
    }
}
