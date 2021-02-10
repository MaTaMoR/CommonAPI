package me.matamor.commonapi.storage.identifier;

import me.matamor.commonapi.storage.database.defaults.single.SingleDatabaseManager;
import me.matamor.commonapi.utils.map.Callback;

import java.sql.SQLException;
import java.util.UUID;

public interface IdentifierDatabase extends SingleDatabaseManager<UUID, Identifier> {

    Identifier loadOrCreate(UUID uuid, String name) throws SQLException;

    Identifier loadByName(String name) throws SQLException;

    Identifier loadById(int id)  throws SQLException;

    void loadByIdAsync(int id, Callback<Identifier> callback);

    boolean updateName(int id, String name) throws SQLException;

    void updateNameAsync(int id, String name, Callback<Boolean> callback);

}
