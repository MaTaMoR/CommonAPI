package me.matamor.commonapi.utils.serializer;

import me.matamor.commonapi.utils.ConfigUtils;
import me.matamor.commonapi.utils.PrimitiveUtils;

import java.util.Map;

public interface Serializer<T> {

    Object serialize(T value) throws SerializationException;

    T deserialize(Object serialized) throws SerializationException;

    default Object get(Map<String, Object> map, String key) throws SerializationException {
        Object value = map.get(key);
        if (value == null) {
            throw new SerializationException(String.format("[%s] Missing map key %s", getClass().getSimpleName(), key));
        }

        return value;
    }

    default <V> V get(Map<String, Object> map, String key, Class<V> clazz) throws SerializationException {
        Object value = map.get(key);
        if (value == null) {
            throw new SerializationException(String.format("[%s] Missing map key %s", getClass().getSimpleName(), key));
        }

        if (PrimitiveUtils.isInstance(clazz, value)) {
            return (V) value;
        } else {
            throw new SerializationException(String.format("Invalid value '%s': Needed '%s' / Value '%s'", key, clazz, value.getClass()));
        }
    }

    default Map<String, Object> asMap(Object object) throws SerializationException {
        Map<String, Object> map = ConfigUtils.asMap(object);
        if (map == null) throw new SerializationException("Provided Value isn't a valid Section");

        return map;
    }
}
