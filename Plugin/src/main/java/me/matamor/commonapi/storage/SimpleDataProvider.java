package me.matamor.commonapi.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.storage.entries.DataEntry;
import me.matamor.commonapi.storage.entry.EntryDataStorage;
import me.matamor.commonapi.storage.identifier.Identifier;

@AllArgsConstructor
public abstract class SimpleDataProvider<T extends DataEntry> implements DataProvider<T> {

    @Getter
    private final Class<T> dataClass;

    @Getter
    private final EntryDataStorage<Identifier, T> storage;

}
