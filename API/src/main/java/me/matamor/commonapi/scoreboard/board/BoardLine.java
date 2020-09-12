package me.matamor.commonapi.scoreboard.board;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BoardLine {

    /**
     * Gets the text for this BoardLine
     * @param player target player, may be null
     * @return String can't be null
     */

    @NotNull
    String getText(@Nullable Player player);

}
