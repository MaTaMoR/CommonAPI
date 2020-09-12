package me.matamor.commonapi.scoreboard.board;

public interface UpdatingBoard extends Board {

    long getDelay();

    long getLastUpdate();

    boolean update(long now);

}
