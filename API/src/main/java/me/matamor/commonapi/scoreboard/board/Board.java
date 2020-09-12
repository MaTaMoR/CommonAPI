package me.matamor.commonapi.scoreboard.board;

import me.matamor.commonapi.scoreboard.nms.NMSViewable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface Board extends NMSViewable {

    /**
     * Gets the id of the Board, it's a integer incremented with each board
     * @return an integer
     */

    int getId();

    /**
     * Gets the BoardTemplate
     * @return the template used to make this Scoreboard, may be null!
     */

    @Nullable
    BoardTemplate getTemplate();

    /**
     * Checks if a Template is set
     * @return true if a Template is set!
     */

    boolean hasTemplate();

    /**
     * Sets the DisplayName
     * @param displayName the DisplayName to be set
     * @return the DisplayName that was just set, may be null!
     */

    @Nullable
    <T extends BoardLine> T setDisplayName(T displayName);

    /**
     * Gets the displayName
     * @return the displayName, may be null!
     */

    @Nullable
    BoardLine getDisplayName();


    /**
     * Sets a BoardLine in a row!
     * @param row the target row
     * @param entry the entry that's going to be placed
     * @return the same entry that was placed, may be null!
     */

    @Nullable
    <T extends BoardLine> T setLine(int row, @Nullable T entry);

    /**
     * Gets the BoardLine from a row
     * @param row the target row
     * @return the BoardLine in the target row
     */

    @Nullable
    BoardLine getLine(int row);

    /**
     * Gets the Viewers
     * @return a Collection of viewers, Player, can't be null!
     */

    @NotNull
    Collection<Player> getViewers();

    /**
     * Hides the Scoreboard to all the viewers and then cleans the List
     */

    void clear();

}
