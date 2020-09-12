package me.matamor.commonapi.utils.container;

import me.matamor.commonapi.utils.CastUtils;
import me.matamor.commonapi.utils.PrimitiveUtils;

import java.util.HashMap;
import java.util.Map;

public class SimpleDataContainer implements DataContainer {

    private final Map<String, Object> container = new HashMap<>();

    @Override
    public DataContainer put(String key, Object value) {
        this.container.put(key, value);

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getObject(String key, Class<T> clazz, T defaultValue) {
        Object value = this.container.get(key);

        if (PrimitiveUtils.isInstance(clazz, value)) {
            return (T) value;
        } else {
            return defaultValue;
        }
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public String getString(String key, String defaultValue) {
        Object object = this.container.get(key);
        if (object == null) {
            return defaultValue;
        } else {
            try {
                return CastUtils.asString(object);
            } catch (CastUtils.FormatException e) {
                return defaultValue;
            }
        }
    }

    @Override
    public int getInt(String key) {
        return getInt(key, -1);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        Object object = this.container.get(key);
        if (object == null) {
            return defaultValue;
        } else {
            try {
                return CastUtils.asInt(object);
            } catch (CastUtils.FormatException e) {
                return defaultValue;
            }
        }
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, -1.0);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        System.out.println("Getting double: " + key);
        Object object = this.container.get(key);
        if (object == null) {
            System.out.println("Object is null!");
            return defaultValue;
        } else {
            try {
                return CastUtils.asDouble(object);
            } catch (CastUtils.FormatException e) {
                e.printStackTrace();
                return defaultValue;
            }
        }
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        Object object = this.container.get(key);
        if (object == null) {
            return defaultValue;
        } else {
            try {
                return CastUtils.asBoolean(object);
            } catch (CastUtils.FormatException e) {
                return defaultValue;
            }
        }
    }
}
