package me.matamor.commonapi.storage.database.defaults.single;

import me.matamor.commonapi.storage.database.DatabaseException;
import me.matamor.commonapi.storage.database.SimpleSQLDatabaseManager;
import me.matamor.commonapi.storage.database.defaults.multi.MultiDatabaseManager;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SimpleMultiSQLDatabaseManager<K, V> extends SimpleSQLDatabaseManager implements MultiDatabaseManager<K, V> {

    public SimpleMultiSQLDatabaseManager(Plugin plugin, String name, double queriesVersion) {
        super(plugin, name, queriesVersion);
    }

    public SimpleMultiSQLDatabaseManager(Plugin plugin, String name, double queriesVersion, boolean open) {
        super(plugin, name, queriesVersion, open);
    }

    @Override
    public void onConnect(Connection connection) throws SQLException {
        for (String query : getCreateQueries()) {
            connection.createStatement().execute(query);
        }
    }

    @Override
    public void save(V save) {
        try {
            try (Connection connection = getConnection()) {
                saveMulti(save, connection);
            }
        } catch (SQLException e) {
            throw new DatabaseException("[Database: " + getName() + "] Couldn't save data: " + (save == null ? "null" : save.toString()), e);
        }
    }

    @Override
    public V load(K key) {
        try {
            try (Connection connection = getConnection()) {
                return loadMulti(key, connection);
            }
        } catch (SQLException e) {
            throw new DatabaseException("[Database: " + getName() + "] Couldn't loadOrCreate data: " + key.toString(), e);
        }
    }

    @Override
    public void delete(K key) {
        try {
            try (Connection connection = getConnection()) {
                deleteMulti(key, connection);
            }
        } catch (SQLException e) {
            throw new DatabaseException("[Database: " + getName() + "] Couldn't delete data: " + key.toString(), e);
        }
    }
}
