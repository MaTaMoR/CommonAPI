package me.matamor.commonapi.storage.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.matamor.commonapi.CommonAPI;
import me.matamor.commonapi.storage.database.settings.ConnectionSettings;
import me.matamor.commonapi.storage.database.settings.ConnectionSettingsType;

public class ConnectionHandler {

    @Getter
    private final DatabaseManager databaseManager;

    @Getter
    private final ConnectionSettings connectionSettings;

    @Getter
    private ConnectionType connectionType;

    @Getter
    private HikariDataSource dataSource;

    public ConnectionHandler(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.connectionSettings = CommonAPI.getInstance().getConnectionSettingsManager().getOrCreate(databaseManager.getName());

        //Get default connection type, if null set to SQLite
        Object connectionType = this.connectionSettings.get(ConnectionSettingsType.TYPE);
        if (!(connectionType instanceof String) || ((this.connectionType = ConnectionType.getByName((String) connectionType)) == null)) {
            this.connectionType = ConnectionType.SQLITE;
        }
    }

    //CREAR HIKARI, SI AKI LO CREAS!!
    public HikariDataSource openConnection() throws DatabaseException {
        return (this.dataSource = this.connectionType.openConnection(this.databaseManager));
    }
}
