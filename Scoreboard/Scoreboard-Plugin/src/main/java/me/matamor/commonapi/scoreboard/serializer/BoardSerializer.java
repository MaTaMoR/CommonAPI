package me.matamor.commonapi.scoreboard.serializer;

import me.matamor.commonapi.scoreboard.board.BoardLine;
import me.matamor.commonapi.scoreboard.board.BoardTemplate;
import me.matamor.commonapi.utils.CastUtils;
import me.matamor.commonapi.utils.serializer.SerializationException;
import me.matamor.commonapi.utils.serializer.Serializer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoardSerializer implements Serializer<BoardTemplate> {

    private final BoardLineSerializer lineSerializer = new BoardLineSerializer();

    @Override
    public Map<String, Object> serialize(BoardTemplate template) throws SerializationException {
        Map<String, Object> map = new LinkedHashMap<>();

        if (template.getDisplayName() != null) {
            map.put("DisplayName", this.lineSerializer.serialize(template.getDisplayName()));
        }

        Map<String, Object> serialized = new LinkedHashMap<>();
        for (Entry<Integer, BoardLine> entry : template.getLines().entrySet()) {
            serialized.put(String.valueOf(entry.getKey()), this.lineSerializer.serialize(entry.getValue()));
        }

        map.put("Lines", serialized);

        return map;
    }

    @Override
    public BoardTemplate deserialize(Object serialized) throws SerializationException {
        Map<String, Object> map = asMap(serialized);

        BoardLine displayName = null;

        if (map.containsKey("DisplayName")) {
            displayName = this.lineSerializer.deserialize(map.get("DisplayName"));
        }

        Map<Integer, BoardLine> entries = new LinkedHashMap<>();

        for (Entry<String, Object> entry : asMap(map.get("Lines")).entrySet()) {
            entries.put(CastUtils.asInt(entry.getKey()), this.lineSerializer.deserialize(entry.getValue()));
        }

        return new BoardTemplate(displayName, entries);
    }
}
