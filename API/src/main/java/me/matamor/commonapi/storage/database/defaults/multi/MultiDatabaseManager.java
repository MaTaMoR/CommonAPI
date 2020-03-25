package me.matamor.commonapi.storage.database.defaults.multi;

import me.matamor.commonapi.storage.database.EntryConnectionDatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface MultiDatabaseManager<K, V> extends EntryConnectionDatabaseManager<K, V> {

    Collection<String> getCreateQueries();

    void saveMulti(V value, Connection connection) throws SQLException;

    V loadMulti(K key, Connection connection) throws SQLException;

    void deleteMulti(K key, Connection connection) throws SQLException;

}
