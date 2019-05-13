package me.matamor.commonapi.storage.entries;

import me.matamor.commonapi.storage.DataStorage;
import me.matamor.commonapi.utils.Validate;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStorageManager {

    private final Map<Class<? extends DataStorage>, DataStorage> entries = new ConcurrentHashMap<>();

    public <T extends DataStorage> T registerStorage(T storageManager) {
        Validate.isFalse(this.entries.containsKey(storageManager.getClass()), "The ConnectionHandler " + storageManager.getClass().getName() + " is already registered");

        this.entries.put(storageManager.getClass(), storageManager);

        return storageManager;
    }

    public <T extends DataStorage> T getDatabase(Class<T> clazz) {
        DataStorage database = this.entries.get(clazz);
        return (database == null ? null : (T) database);
    }

    public Collection<DataStorage> getEntries() {
        return Collections.unmodifiableCollection(this.entries.values());
    }

    public void unload() {
        this.entries.values().stream()
            .filter(e -> e instanceof Closeable)
            .map(e -> (Closeable) e)
            .forEach(e -> {
                try {
                    e.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

        this.entries.clear();
    }
}
