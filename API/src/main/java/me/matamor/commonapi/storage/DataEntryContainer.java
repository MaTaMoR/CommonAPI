package me.matamor.commonapi.storage;

import me.matamor.commonapi.storage.entries.DataEntry;
import me.matamor.commonapi.storage.entry.EntryDataStorage;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.utils.map.Callback;

public interface DataEntryContainer<T extends DataEntry> {

    T createEntry(Identifier identifier);

    EntryDataStorage<Identifier, T> getDataStorage();

    void save();

    void saveAsync(Callback<Boolean> callback);

}
