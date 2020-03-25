package me.matamor.commonapi.storage.database.defaults.single;

import me.matamor.commonapi.storage.database.EntryConnectionDatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SingleDatabaseManager<K, V> extends EntryConnectionDatabaseManager<K, V> {

    void executeInsertQuery(PreparedStatement statement, V data) throws SQLException;

    void createSelectQuery(PreparedStatement statement, K key) throws SQLException;

    V deserialize(ResultSet resultSet, K key) throws SQLException;

    void executeRemoveQuery(PreparedStatement statement, K key) throws SQLException;

}
