package me.matamor.commonapi.scoreboard.board.lines;

import me.matamor.commonapi.utils.StringUtils;
import me.matamor.commonapi.utils.replacement.PlayerVariables;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AnimationBoardLine extends IntervalBoardLine {

    private final List<String> text;
    private final boolean fixed;

    private int maxLength;
    private int position;

    public AnimationBoardLine(List<String> text, int interval) {
        this(text, interval, false);
    }

    public AnimationBoardLine(List<String> text, int interval, boolean fixed) {
        super(interval);

        this.text = text;
        this.fixed = fixed;

        for (String line : text) {
            if (StringUtils.removeColor(line).length() > this.maxLength) {
                this.maxLength = line.length();
            }
        }
    }

    @Override
    public String getText(Player player) {
        String line;

        if(should()) {
            line = next();
        } else {
            line = this.text.get(this.position);
        }

        if (this.fixed) {
            line = center(line);
        }

        return StringUtils.color(PlayerVariables.replace(line, player));
    }

    public boolean isFixed() {
        return fixed;
    }

    private String next() {
        position++;

        if(position >= text.size()) {
            position = 0;
        }

        lastUpdate = System.currentTimeMillis();
        return text.get(position);
    }

    private String center(String text) {
        if (this.fixed) {
            return StringUtils.repeat(' ', (this.maxLength / 2) - StringUtils.removeColor(text).length() / 2) + text;
        } else {
            return text;
        }
    }

    public Collection<String> getLines() {
        return Collections.unmodifiableList(this.text);
    }
}
