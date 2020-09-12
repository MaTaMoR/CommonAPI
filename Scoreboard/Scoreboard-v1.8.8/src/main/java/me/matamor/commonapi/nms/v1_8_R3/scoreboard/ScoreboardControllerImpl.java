package me.matamor.commonapi.nms.v1_8_R3.scoreboard;

import me.matamor.commonapi.nms.NMSVersion;
import me.matamor.commonapi.scoreboard.ScoreboardController;
import me.matamor.commonapi.scoreboard.nms.NMSScoreboard;
import org.jetbrains.annotations.NotNull;

public class ScoreboardControllerImpl implements ScoreboardController {

    @Override
    public NMSVersion getVersion() {
        return NMSVersion.v1_8_R3;
    }

    @Override
    public @NotNull NMSScoreboard createScoreboard() {
        return new NMSScoreboardImpl();
    }
}
