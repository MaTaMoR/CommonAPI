package me.matamor.commonapi.storage.entries.defaults;

import me.matamor.commonapi.storage.DataEntryContainer;
import me.matamor.commonapi.storage.entries.DataEntry;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.utils.Name;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListDataEntry<T extends Name> extends SimpleDataEntry {

    private final Map<String, T> entries = new HashMap<>();

    public ListDataEntry(DataEntryContainer<DataEntry> container, Identifier identifier) {
        super(container, identifier);
    }

    public void add(T data) {
        this.entries.put(data.getName(), data);
    }

    public T get(String name) {
        return this.entries.get(name);
    }

    public void remove(T value) {
        remove(value.getName());
    }

    public void remove(String name) {
        this.entries.remove(name);
    }

    public boolean has(T value) {
        return has(value.getName());
    }

    public boolean has(String name) {
        return this.entries.containsKey(name);
    }

    public Collection<T> getValues() {
        return Collections.unmodifiableCollection(this.entries.values());
    }

}
