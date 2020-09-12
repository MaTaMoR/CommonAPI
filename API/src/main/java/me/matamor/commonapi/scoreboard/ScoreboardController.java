package me.matamor.commonapi.scoreboard;

import me.matamor.commonapi.nms.NMSController;
import me.matamor.commonapi.scoreboard.board.Board;
import me.matamor.commonapi.scoreboard.nms.NMSScoreboard;
import org.jetbrains.annotations.NotNull;

public interface ScoreboardController extends NMSController {

    /**
     * Creates a NMSScoreboard
     * @return a new NMSScoreboard, can't be null!
     */

    @NotNull
    NMSScoreboard createScoreboard();

}
