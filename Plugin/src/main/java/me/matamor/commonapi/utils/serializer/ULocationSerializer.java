package me.matamor.commonapi.utils.serializer;

import me.matamor.commonapi.utils.ULocation;

import java.util.LinkedHashMap;
import java.util.Map;

public class ULocationSerializer implements Serializer<ULocation> {

    @Override
    public Map<String, Object> serialize(ULocation value) throws SerializationException {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("World", value.getWorldName());
        map.put("X", value.getX());
        map.put("Y", value.getY());
        map.put("Z", value.getZ());
        map.put("Yaw", value.getYaw());
        map.put("Pitch", value.getPitch());

        return map;
    }

    @Override
    public ULocation deserialize(Object serialized) throws SerializationException {
        Map<String, Object> map = asMap(serialized);

        String worldName = get(map, "World", String.class);
        double x = get(map, "X", double.class);
        double y = get(map, "Y", double.class);
        double z = get(map, "Z", double.class);
        float yaw = get(map, "Yaw", Double.class).floatValue();
        float pitch = get(map, "Pitch", Double.class).floatValue();

        return new ULocation(worldName, x, y, z, yaw, pitch);
    }
}
