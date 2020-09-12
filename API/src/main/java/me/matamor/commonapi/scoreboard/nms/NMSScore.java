package me.matamor.commonapi.scoreboard.nms;

import org.jetbrains.annotations.NotNull;

public interface NMSScore extends NMSViewable {

    /**
     * Gets the Objective
     * @return the Objective, can't be null!
     */

    @NotNull
    NMSObjective getObjective();

    /**
     * Gets the Entry
     * @return the Entry of this Score.
     */

    @NotNull
    String getEntry();

    /**
     * Gets the Score
     * @return the Score set.
     */

    int getScore();

    /**
     * Sets the Score
     * @param score the target Score to be set;
     */

    void setScore(int score);

}
