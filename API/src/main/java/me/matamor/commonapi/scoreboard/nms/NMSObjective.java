package me.matamor.commonapi.scoreboard.nms;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public interface NMSObjective extends NMSViewable {

    /**
     * Gets the Scoreboard
     * @return the Scoreboard, the parent of this Objective, can't be null!
     */

    @NotNull
    NMSScoreboard getScoreboard();

    /**
     * Gets the DisplaySlot
     * @return the DisplaySlot being used for this Objective, can't be null!
     */

    @NotNull
    DisplaySlot getDisplaySlot();

    /**
     * Gets the Name
     * @return the Name of the Objective, can't be null!
     */

    @NotNull
    String getName();

    /**
     * Gets the DisplayName
     * @return the DisplayName of the Objective, can't be null!
     */

    @NotNull
    String getDisplayName();

    /**
     * Sets the DisplayName
     * @param displayName the target DisplayName to be set, can't be null!
     */

    void setDisplayName(@NotNull String displayName);

    /**
     * Gets the Score of the target string entry
     * @param entry the target entry, can't be null!
     * @return the NMSScore, can't be null!
     */

    @NotNull
    NMSScore getScore(@NotNull String entry);

    /**
     * Removes the Score of the target string entry
     * @param entry the target entry, can't be null!
     */

    void removeScore(@NotNull String entry);

    /**
     * Gets the Scores
     * @return the scores, can't be null!
     */

    @NotNull
    Collection<NMSScore> getScores();

    /**
     * Clears the scores, need to update to show change to players!
     */

    void clearScores();


}