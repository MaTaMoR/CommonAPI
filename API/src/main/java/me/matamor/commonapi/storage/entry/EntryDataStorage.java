package me.matamor.commonapi.storage.entry;

import me.matamor.commonapi.storage.DataStorage;
import me.matamor.commonapi.utils.map.Callback;

public interface EntryDataStorage<K, V> extends DataStorage {

    void save(V save);

    default void saveAsync(V value, Callback<Boolean> callback) {
        runAsync(() -> {
            Exception exception = null;

            try {
                save(value);
            } catch (Exception e) {
                exception = e;
            }

            if (callback != null) {
                callback.done(true, exception);
            }
        });
    }

    V load(K key);

    default void loadAsync(K key, Callback<V> callback) {
        runAsync(() -> callback.done(load(key), null));
    }

    void delete(K key);

    default void deleteAsync(K key, Callback<Boolean> callback) {
        runAsync(() -> {
            Exception exception = null;

            try {
                delete(key);
            } catch (Exception e) {
                exception = e;
            }

            if (callback != null) {
                callback.done(true, exception);
            }
        });
    }
}
