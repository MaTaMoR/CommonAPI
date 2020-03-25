package me.matamor.commonapi.storage.database;

import me.matamor.commonapi.storage.DataStorage;
import me.matamor.commonapi.utils.Name;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionDatabaseManager extends DataStorage, Name, Closeable {

    boolean connect();

    boolean isClosed();

    Connection getConnection() throws SQLException;

    void close();

}
