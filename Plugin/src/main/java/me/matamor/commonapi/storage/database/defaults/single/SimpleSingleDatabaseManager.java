package me.matamor.commonapi.storage.database.defaults.single;

import me.matamor.commonapi.storage.database.DatabaseException;
import me.matamor.commonapi.storage.database.SimpleDatabaseManager;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SimpleSingleDatabaseManager<K, V> extends SimpleDatabaseManager implements SingleDatabaseManager<K, V> {

    public SimpleSingleDatabaseManager(Plugin plugin, String name, double queriesVersion) {
        super(plugin, name, queriesVersion);
    }

    public SimpleSingleDatabaseManager(Plugin plugin, String name, double queriesVersion, boolean open) {
        super(plugin, name, queriesVersion, open);
    }

    @Override
    public void onConnect(Connection connection) throws SQLException {
        connection.createStatement().execute(getQueries().getCreate());
    }

    @Override
    public void save(V data) {
        try {
            //Create connection
            try (Connection connection = getConnection()) {
                //Create statement
                try (PreparedStatement preparedStatement = connection.prepareStatement(getQueries().getInsert())) {
                    //Execute query
                    executeInsertQuery(preparedStatement, data);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("[Database: " + getName() + "] Couldn't save data: " + (data == null ? "null" : data.toString()), e);
        }
    }

    @Override
    public V load(K key) {
        try {
            //Create connection
            try (Connection connection = getConnection()) {
                //Create statement
                try (PreparedStatement preparedStatement = connection.prepareStatement(getQueries().getSelect())) {
                    //Insert data into statement
                    createSelectQuery(preparedStatement, key);

                    //Execute query and get data
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            return deserialize(resultSet, key);
                        }
                    }
                }
            }

            return null;
        } catch (SQLException e) {
            throw new DatabaseException("[Database: " + getName() + "] Couldn't loadOrCreate data from: " + key.toString(), e);
        }
    }

    @Override
    public void delete(K key) {
        try {
            //Create connection
            try (Connection connection = getConnection()) {
                //Create statement
                try (PreparedStatement preparedStatement = connection.prepareStatement(getQueries().getDelete())) {
                    //Execute delete query
                    executeRemoveQuery(preparedStatement, key);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("[Database: " + getName() + "] Couldn't delete data from: " + key.toString(), e);
        }
    }
}
