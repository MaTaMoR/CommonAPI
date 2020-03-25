package me.matamor.commonapi.storage.database;

import me.matamor.commonapi.storage.entry.EntryDataStorage;

public interface EntryConnectionDatabaseManager<K, V> extends ConnectionDatabaseManager, EntryDataStorage<K, V> {

}
