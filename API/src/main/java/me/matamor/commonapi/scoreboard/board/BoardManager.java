package me.matamor.commonapi.scoreboard.board;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public interface BoardManager {

    /**
     * Gets the Plugin
     * @return the Plugin, the parent if this manager!
     */

    @NotNull
    Plugin getPlugin();

    /**
     * Gets or creates if null a Board for the target UUID
     * @param uuid the target UUID, can't be null!
     * @return the Board for the target UUID, can't be null!
     */

    @NotNull
    Board getOrCreateBoard(@NotNull UUID uuid);

    /**
     * Gets a Board for the target UUID
     * @param uuid the target UUID, can't be null!
     * @return the Board for the target UUID, can be null!
     */

    @NotNull
    Board getBoard(@NotNull UUID uuid);

    /**
     * Removes the Board of the target UUID
     * @param uuid the target UUID, can't be null!
     * @return true if removed
     */

    boolean removeBoard(@NotNull UUID uuid);

    /**
     * Gets all the registered Boards
     * @return the registered Boards, can't be null!
     */

    @NotNull
    Collection<Map.Entry<UUID, Board>> getBoards();

    /**
     * Clears all the Boards
     */

    void clear();

}
