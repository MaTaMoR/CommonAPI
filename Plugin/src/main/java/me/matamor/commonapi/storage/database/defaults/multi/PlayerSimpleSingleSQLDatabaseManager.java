package me.matamor.commonapi.storage.database.defaults.multi;

import me.matamor.commonapi.storage.database.defaults.single.SimpleSingleSQLDatabaseManager;
import me.matamor.commonapi.storage.identifier.SimpleIdentifier;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class PlayerSimpleSingleSQLDatabaseManager<V> extends SimpleSingleSQLDatabaseManager<SimpleIdentifier, V> {

    public PlayerSimpleSingleSQLDatabaseManager(Plugin plugin, String name, double queriesVersion) {
        super(plugin, name, queriesVersion);
    }

    public PlayerSimpleSingleSQLDatabaseManager(Plugin plugin, String name, double queriesVersion, boolean open) {
        super(plugin, name, queriesVersion, open);
    }

    @Override
    public void createSelectQuery(PreparedStatement statement, SimpleIdentifier key) throws SQLException {
        statement.setInt(1, key.getId());
    }

    @Override
    public void executeRemoveQuery(PreparedStatement statement, SimpleIdentifier key) throws SQLException {
        statement.setInt(1, key.getId());
    }
}
