package me.matamor.commonapi.storage.entry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

@AllArgsConstructor
public abstract class EmptyEntryDataStorage<K, V> implements EntryDataStorage<K, V> {

    @Getter
    private final Plugin plugin;

    @Override
    public void save(V save) {

    }

    @Override
    public V load(K key) {
        return null;
    }

    @Override
    public void delete(K key) {

    }
}
