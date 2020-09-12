package me.matamor.commonapi.storage.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.storage.database.settings.ConnectionSettings;
import me.matamor.commonapi.storage.database.settings.ConnectionSettingsType;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@AllArgsConstructor
public enum ConnectionType {

    MYSQL("MySQL") {
        @Override
        public HikariDataSource openConnection(SQLDatabaseManager databaseManager) throws DatabaseException {
            ConnectionSettings connectionSettings = databaseManager.getConnectionHandler().getConnectionSettings();
            
            Object ip = connectionSettings.get(ConnectionSettingsType.IP);
            Object port = connectionSettings.get(ConnectionSettingsType.PORT);
            Object database = connectionSettings.get(ConnectionSettingsType.DATABASE);

            databaseManager.getPlugin().getLogger().log(Level.INFO,
                    String.format("[Database: %s] Creating MySQL connection to: %s:%s/%s", databaseManager.getName(), ip, port, database));

            HikariConfig config = new HikariConfig();

            config.setPoolName(databaseManager.getName());
            config.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + database);
            config.setUsername((String) connectionSettings.get(ConnectionSettingsType.USERNAME));
            config.setPassword((String) connectionSettings.get(ConnectionSettingsType.PASSWORD));

            config.setDriverClassName("com.mysql.jdbc.Driver");

            config.setMaximumPoolSize(3);
            config.setAutoCommit(true);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            return new HikariDataSource(config);
        }
    },
    SQLITE("SQLite") {
        @Override
        public HikariDataSource openConnection(SQLDatabaseManager databaseManager) throws DatabaseException {
            ConnectionSettings connectionSettings = databaseManager.getConnectionHandler().getConnectionSettings();

            File databasesFolder = new File(databaseManager.getPlugin().getDataFolder(), "Databases");
            if (!databasesFolder.exists()) {
                databasesFolder.mkdirs();
            }

            File databaseFile = new File(databasesFolder, connectionSettings.get(ConnectionSettingsType.DATABASE) + ".db");
            if (!databaseFile.exists()) {
                try {
                   databaseFile.createNewFile();
                } catch (IOException e) {
                   throw new DatabaseException("Couldn't create database file (" + databaseFile.getPath() + ")", e);
                }
            }

            HikariConfig config = new HikariConfig();
            config.setPoolName(databaseManager.getName());
            config.setDriverClassName("org.sqlite.JDBC");
            config.setJdbcUrl("jdbc:sqlite:" + databaseFile.getPath());
            config.setConnectionTestQuery("SELECT 1;");
            config.setMaxLifetime(60000); // 60 Sec
            config.setIdleTimeout(45000); // 45 Sec
            config.setMaximumPoolSize(3); // 50 Connections (including idle connections)

            return new HikariDataSource(config);
        }
    };

    public abstract HikariDataSource openConnection(SQLDatabaseManager databaseManager) throws DatabaseException;

    @Getter
    private final String displayName;

    public static ConnectionType getByName(String name) {
        for (ConnectionType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }

        return null;
    }

}
