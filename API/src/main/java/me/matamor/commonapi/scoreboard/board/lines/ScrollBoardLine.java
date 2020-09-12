package me.matamor.commonapi.scoreboard.board.lines;

import me.matamor.commonapi.utils.StringUtils;
import me.matamor.commonapi.utils.replacement.PlayerVariables;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScrollBoardLine extends IntervalBoardLine {

    private static final char colourChar = '&';

    private final String message;
    private final List<String> list;
    private final int width;
    private final int spaceBetween;

    private int position;
    private ChatColor colour = ChatColor.RESET;
    private String lastString;

    public ScrollBoardLine(String message, int width, int spaceBetween, int interval) {
        super(interval);

        this.message = message;
        this.width = width;
        this.spaceBetween = spaceBetween;
        this.list = new ArrayList<>();

        // Validation
        // String is too short for window
        if (message.length() < width) {
            StringBuilder sb = new StringBuilder(message);
            while (sb.length() < width)
                sb.append(" ");
            message = sb.toString();
        }

        // Allow for colours which add 2 to the width
        width -= 2;

        // Invalid width/space size
        if (width < 1) {
            width = 1;
        }

        if (spaceBetween < 0) {
            spaceBetween = 0;
        }


        // Add substrings
        for (int i = 0; i < message.length() - width; i++)
            list.add(message.substring(i, i + width));

        // Add space between repeats
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < spaceBetween; ++i) {
            list.add(message.substring(message.length() - width + (Math.min(i, width)), message.length()) + space);
            if (space.length() < width) {
                space.append(" ");
            }
        }

        // Wrap
        for (int i = 0; i < width - spaceBetween; ++i) {
            list.add(message.substring(message.length() - width + spaceBetween + i, message.length()) + space + message.substring(0, i));
        }

        // Join up
        for (int i = 0; i < spaceBetween; i++) {
            if (i > space.length()) {
                break;
            }

            list.add(space.substring(0, space.length() - i) + message.substring(0, width - (Math.min(spaceBetween, width)) + i));
        }
    }

    @Override
    public String getText(Player player) {
        String text;

        if (should()) {
            text = next();
        } else {
            text = lastString;
        }

        lastString = text;

        return StringUtils.color(PlayerVariables.replace(text, player));
    }

    public String getLine() {
        return message;
    }

    public int getWidth() {
        return width;
    }

    public int getSpaceBetween() {
        return spaceBetween;
    }

    private String next() {
        StringBuilder sb = getNext();
        if (sb.charAt(sb.length() - 1) == colourChar)
            sb.setCharAt(sb.length() - 1, ' ');

        if (sb.charAt(0) == colourChar) {
            ChatColor c = ChatColor.getByChar(sb.charAt(1));
            if (c != null) {
                colour = c;
                sb = getNext();
                if (sb.charAt(0) != ' ') {
                    sb.setCharAt(0, ' ');
                }
            }
        }

        lastUpdate = System.currentTimeMillis();
        return colour + sb.toString();
    }

    private StringBuilder getNext() {
        return new StringBuilder(list.get(position++ % list.size()));
    }
}
