package me.matamor.commonapi.scoreboard.board.lines;

import lombok.Getter;
import me.matamor.commonapi.scoreboard.board.BoardLine;

public abstract class IntervalBoardLine implements BoardLine {

    @Getter
    private final int interval;

    @Getter
    private final long millisInterval;

    @Getter
    protected long lastUpdate;

    public IntervalBoardLine(int interval) {
        this.interval = interval;
        this.millisInterval = interval * 50;
    }

    public boolean should() {
        return (this.millisInterval + this.lastUpdate) - System.currentTimeMillis() <= 0;
    }
}
