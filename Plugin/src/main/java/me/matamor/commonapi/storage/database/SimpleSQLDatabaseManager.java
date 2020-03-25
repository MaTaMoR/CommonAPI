package me.matamor.commonapi.storage.database;

import lombok.Getter;
import me.matamor.commonapi.storage.database.queries.Queries;
import me.matamor.commonapi.storage.database.queries.SimpleQueries;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class SimpleSQLDatabaseManager implements SQLDatabaseManager {

    @Getter
    private final Plugin plugin;

    @Getter
    private final String name;

    @Getter
    private final ConnectionHandler connectionHandler;

    @Getter
    private final Queries queries;

    @Getter
    private final ExecutorService executor;

    public SimpleSQLDatabaseManager(Plugin plugin, String name, double queriesVersion) {
        this(plugin, name, queriesVersion, false);
    }

    public SimpleSQLDatabaseManager(Plugin plugin, String name, double queriesVersion, boolean open) {
        this.plugin = plugin;
        this.name = name;
        this.connectionHandler = new ConnectionHandler(this);

        this.queries = new SimpleQueries(this, queriesVersion);
        this.queries.load();

        this.executor = Executors.newSingleThreadExecutor();

        if (open) {
            connect();
        }
    }
}
