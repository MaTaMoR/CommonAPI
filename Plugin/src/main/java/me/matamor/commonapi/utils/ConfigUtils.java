package me.matamor.commonapi.utils;

import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public final class ConfigUtils {

    private ConfigUtils() {

    }

    public static Map<String, Object> asMap(Object object) {
        if (object instanceof ConfigurationSection) {
            return ((ConfigurationSection) object).getValues(false);
        } else if (object instanceof Map) {
            return ((Map<String, Object>) object);
        }

        return null;
    }
}
