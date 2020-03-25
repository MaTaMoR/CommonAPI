package me.matamor.commonapi.utils.serializer;

import me.matamor.commonapi.utils.Validate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializationManager {

    private static SerializationManager instance;

    public static SerializationManager getInstance() {
        if (instance == null) {
            instance = new SerializationManager();
        }

        return instance;
    }

    private SerializationManager() {

    }

    private final Map<Class<?>, Serializer> entries = new ConcurrentHashMap<>();

    public <T> void register(Class<T> clazz, Serializer<T> serializer) {
        Validate.notNull(serializer, "Can't register a Serializer for a null class!");
        Validate.notNull(serializer, "Can't register a null Serializer!");

        this.entries.put(clazz, serializer);
    }

    public <T> Serializer<T> getSerializer(Class<T> clazz) {
        Validate.notNull(clazz, "Can't get a Serializer out of a null class!");

        Serializer serializer = this.entries.get(clazz);
        return (serializer == null ? null : (Serializer<T>) serializer);
    }

    public Object serialize(Object object) throws SerializationException {
        Validate.notNull(object, "Can't serialize a null object!");

        Serializer serializer = this.entries.get(object.getClass());
        Validate.notNull(serializer, "Serializer for class '" + object.getClass().getSimpleName() + "' is missing!");

        return serializer.serialize(object);
    }

    public <T> T deserialize(Object object, Class<T> clazz) throws SerializationException {
        Validate.notNull(object, "Can't deserialize a null object!");
        Validate.notNull(clazz, "Can't deserialize an Object with a null Class!");

        Serializer<T> serializer = this.entries.get(clazz);
        Validate.notNull(serializer, "Serializer for class '" + clazz.getSimpleName() + "' is missing!");

        return serializer.deserialize(object);
    }
}
