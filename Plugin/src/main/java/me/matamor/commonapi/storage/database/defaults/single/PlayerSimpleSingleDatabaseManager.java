package me.matamor.commonapi.storage.database.defaults.single;

import me.matamor.commonapi.storage.identifier.Identifier;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class PlayerSimpleSingleDatabaseManager<V> extends SimpleSingleDatabaseManager<Identifier, V> {

    public PlayerSimpleSingleDatabaseManager(Plugin plugin, String name, double queriesVersion) {
        super(plugin, name, queriesVersion);
    }

    public PlayerSimpleSingleDatabaseManager(Plugin plugin, String name, double queriesVersion, boolean open) {
        super(plugin, name, queriesVersion, open);
    }

    @Override
    public void createSelectQuery(PreparedStatement statement, Identifier key) throws SQLException {
        statement.setInt(1, key.getId());
    }

    @Override
    public void executeRemoveQuery(PreparedStatement statement, Identifier key) throws SQLException {
        statement.setInt(1, key.getId());
    }
}
