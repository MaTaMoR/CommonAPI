package me.matamor.commonapi.scoreboard.serializer;

import me.matamor.commonapi.scoreboard.board.BoardLine;
import me.matamor.commonapi.scoreboard.board.lines.AnimationBoardLine;
import me.matamor.commonapi.scoreboard.board.lines.IntervalBoardLine;
import me.matamor.commonapi.scoreboard.board.lines.ScrollBoardLine;
import me.matamor.commonapi.scoreboard.board.lines.TextBoardLine;
import me.matamor.commonapi.utils.CastUtils;
import me.matamor.commonapi.utils.serializer.SerializationException;
import me.matamor.commonapi.utils.serializer.Serializer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoardLineSerializer implements Serializer<BoardLine> {

    @Override
    public Map<String, Object> serialize(BoardLine line) throws SerializationException {
        Map<String, Object> map = new LinkedHashMap<>();

        if (line instanceof TextBoardLine) {
            map.put("Line", ((TextBoardLine) line).getText());
        } else if (line instanceof ScrollBoardLine) {
            ScrollBoardLine scroll = (ScrollBoardLine) line;

            map.put("Line", scroll.getLine());
            map.put("Width", scroll.getWidth());
            map.put("SpaceBetween", scroll.getSpaceBetween());
            map.put("Scrollable", true);
        } else if (line instanceof AnimationBoardLine) {
            AnimationBoardLine animation = (AnimationBoardLine) line;

            map.put("Line", animation.getLines());
            map.put("Fixed", animation.isFixed());
        }

        if (line instanceof IntervalBoardLine) {
            map.put("Interval", ((IntervalBoardLine) line).getInterval());
        }

        return map;
    }

    @Override
    public BoardLine deserialize(Object serialized) throws SerializationException {
        if (serialized instanceof String) {
            return new TextBoardLine((String) serialized);
        } else {
            Map<String, Object> map = asMap(serialized);

            BoardLine line;

            Object object = map.get("Line");
            int interval = (map.containsKey("Interval") ? CastUtils.asInt(get(map, "Interval")) : 20);

            if (object instanceof String) {
                String message = (String) object;
                boolean scroll = (map.containsKey("Scrollable") && CastUtils.asBoolean(get(map, "Scrollable")));

                if (scroll) {
                    int width = CastUtils.asInt(get(map, "Width"));
                    int spaceBetween = CastUtils.asInt(get(map, "SpaceBetween"));

                    line = new ScrollBoardLine(message, width, spaceBetween, interval);
                } else {
                    line = new TextBoardLine(message);
                }
            } else if (object instanceof List) {
                List<String> message = (List<String>) object;
                boolean fixed = (map.containsKey("Fixed") && CastUtils.asBoolean(get(map, "Fixed")));

                line = new AnimationBoardLine(message, interval, fixed);
            } else {
                throw new SerializationException("Not a valid line");
            }

            return line;
        }
    }
}
