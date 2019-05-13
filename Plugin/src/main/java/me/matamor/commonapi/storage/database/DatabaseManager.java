package me.matamor.commonapi.storage.database;

import me.matamor.commonapi.storage.DataStorage;
import me.matamor.commonapi.storage.database.queries.Queries;
import me.matamor.commonapi.storage.database.queries.builder.QueryBuilder;
import me.matamor.commonapi.storage.database.queries.builder.SimpleQueryBuilder;
import me.matamor.commonapi.utils.Name;
import me.matamor.commonapi.utils.Validate;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseManager extends DataStorage, Name, Closeable {

    ConnectionHandler getConnectionHandler();

    Queries getQueries();

    default QueryBuilder queryBuilder() {
        return new SimpleQueryBuilder(this);
    }

    default void onConnect(Connection connection) throws SQLException {

    }

    default boolean loadDatabase() {
        try {
            //Create connection
            try (Connection connection = getConnectionHandler().openConnection().getConnection()) {

                //Execute create table query
                onConnect(connection);
                return true;
            }
        } catch (DatabaseException | SQLException e) {
            throw new DatabaseException("[Database: " + getName() + "] Couldn't create connection!", e);
        }
    }

    default boolean isClosed() {
        return getConnectionHandler().getDataSource() == null || getConnectionHandler().getDataSource().isClosed();
    }

    default void close() {
        if (!isClosed()) {
            getConnectionHandler().getDataSource().close();
        }
    }

    default Connection getConnection() throws SQLException {
        Validate.isFalse(isClosed(), "Connection is closed!");

        return getConnectionHandler().getDataSource().getConnection();
    }
}
