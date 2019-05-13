package me.matamor.commonapi.storage.database;

import me.matamor.commonapi.storage.entry.EntryDataStorage;

public interface EntryDatabaseManager<K, V> extends DatabaseManager, EntryDataStorage<K, V> {

}
