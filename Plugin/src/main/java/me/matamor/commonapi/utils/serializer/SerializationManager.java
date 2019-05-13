package me.matamor.commonapi.utils.serializer;

import me.matamor.commonapi.utils.Validate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializationManager {

    private final Map<Class<?>, Serializer> entries = new ConcurrentHashMap<>();

    public void register(Serializer serializer) {
        Class<?> clazz = serializer.getClass();
        Validate.isFalse(entries.containsKey(clazz), String.format("There's already a Serializer registered for the class %s", clazz.getName()));

        entries.put(clazz, serializer);
    }

    public <T> Serializer<T> getSerializer(Class<T> clazz) {
        Serializer serializer = entries.get(clazz);
        return (serializer == null ? null : (Serializer<T>) serializer);
    }
}
