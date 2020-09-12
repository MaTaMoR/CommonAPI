package me.matamor.commonapi.storage.database.defaults.multi;

import me.matamor.commonapi.storage.database.defaults.single.SimpleMultiSQLDatabaseManager;
import me.matamor.commonapi.storage.identifier.SimpleIdentifier;
import org.bukkit.plugin.Plugin;

public abstract class PlayerSimpleMultiSQLDatabaseManager<V> extends SimpleMultiSQLDatabaseManager<SimpleIdentifier, V> {

    public PlayerSimpleMultiSQLDatabaseManager(Plugin plugin, String name, double queriesVersion) {
        super(plugin, name, queriesVersion);
    }

    public PlayerSimpleMultiSQLDatabaseManager(Plugin plugin, String name, double queriesVersion, boolean open) {
        super(plugin, name, queriesVersion, open);
    }
}
