package me.matamor.commonapi.utils;

import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ConfigUtils {

    private ConfigUtils() {

    }

    public static Map<String, Object> asMap(Object object) {
        if (object instanceof ConfigurationSection) {
            return ((ConfigurationSection) object).getValues(false);
        } else if (object instanceof Map) {
            Map<String, Object> map = new LinkedHashMap<>();

            ((Map<?, ?>) object).entrySet().stream()
                    .filter(e -> e.getValue() instanceof String)
                    .forEach(e -> map.put((String) e.getKey(), e.getValue()));

            return map;
        }

        return null;
    }
}
