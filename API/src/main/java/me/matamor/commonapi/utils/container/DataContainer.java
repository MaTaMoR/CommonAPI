package me.matamor.commonapi.utils.container;

public interface DataContainer {

    DataContainer put(String key, Object value);

    <T> T getObject(String key, Class<T> clazz, T defaultValue);

    String getString(String key);

    String getString(String key, String defaultValue);

    int getInt(String key);

    int getInt(String key, int defaultValue);

    double getDouble(String key);

    double getDouble(String key, double defaultValue);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);

}
