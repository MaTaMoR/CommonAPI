package me.matamor.commonapi.storage;

import me.matamor.commonapi.storage.entries.DataEntry;
import me.matamor.commonapi.storage.entry.EntryDataStorage;
import me.matamor.commonapi.storage.identifier.Identifier;

public interface DataProvider<T extends DataEntry> extends InstanceProvider<T> {

    Class<T> getDataClass();

    EntryDataStorage<Identifier, T> getStorage();

}
