package me.matamor.commonapi.scoreboard;

import me.matamor.commonapi.scoreboard.board.Board;
import me.matamor.commonapi.scoreboard.board.UpdatingBoard;

public interface ScoreboardManager {

    Board createBoard();

    UpdatingBoard createUpdatingBoard(int delay);

    void untrackBoard(Board board);

    void clear();

}
