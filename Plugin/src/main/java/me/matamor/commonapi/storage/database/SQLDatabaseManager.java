package me.matamor.commonapi.storage.database;

import me.matamor.commonapi.storage.database.queries.Queries;
import me.matamor.commonapi.storage.database.queries.builder.QueryBuilder;
import me.matamor.commonapi.storage.database.queries.builder.SimpleQueryBuilder;
import me.matamor.commonapi.utils.Name;
import me.matamor.commonapi.utils.Validate;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public interface SQLDatabaseManager extends ConnectionDatabaseManager, Name, Closeable {

    ConnectionHandler getConnectionHandler();

    Queries getQueries();

    default QueryBuilder queryBuilder() {
        return new SimpleQueryBuilder(this);
    }

    default void onConnect(Connection connection) throws SQLException {

    }

    @Override
    default boolean connect() {
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

    @Override
    default boolean isClosed() {
        return getConnectionHandler().getDataSource() == null || getConnectionHandler().getDataSource().isClosed();
    }

    @Override
    default void close() {
        if (!isClosed()) {
            getConnectionHandler().getDataSource().close();
        }
    }

    @Override
    default Connection getConnection() throws SQLException {
        Validate.isFalse(isClosed(), "Connection is closed!");

        return getConnectionHandler().getDataSource().getConnection();
    }
}
