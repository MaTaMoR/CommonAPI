package me.matamor.commonapi.scoreboard.board.lines;


import me.matamor.commonapi.scoreboard.board.BoardLine;
import me.matamor.commonapi.utils.StringUtils;
import me.matamor.commonapi.utils.replacement.PlayerVariables;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextBoardLine implements BoardLine {

    private final String text;

    public TextBoardLine(String text) {
        this.text = text;
    }

    @Override
    public @NotNull String getText(@Nullable Player player) {
        return StringUtils.color(PlayerVariables.replace(this.text, player));
    }
}
