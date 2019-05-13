package me.matamor.commonapi.config.special;

import lombok.Getter;
import me.matamor.commonapi.utils.serializer.SerializationException;
import me.matamor.commonapi.utils.serializer.Serializer;

import java.lang.reflect.Field;

public class ConfigEntry {

    @Getter
    private final Object instance;

    @Getter
    private final Field field;

    @Getter
    private final String path;

    @Getter
    private final String[] comments;

    @Getter
    private final Serializer serializer;

    public ConfigEntry(Object instance, Field field, String path, String[] comments, Serializer serializer) {
        this.instance = instance;
        this.field = field;
        this.path = path;
        this.comments = comments;
        this.serializer = serializer;

        this.field.setAccessible(true);
    }

    public Class<?> getType() {
        return this.field.getType();
    }

    public Object get() throws IllegalAccessException {
        return this.field.get(this.instance);
    }

    public void set(Object value) throws IllegalAccessException {
        this.field.set(this.instance, value);
    }

    public Object serialize() throws IllegalAccessException, SerializationException {
        return (this.serializer == null ? get() : this.serializer.serialize(get()));
    }
}
