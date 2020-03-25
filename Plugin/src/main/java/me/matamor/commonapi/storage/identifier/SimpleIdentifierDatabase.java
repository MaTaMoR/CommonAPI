package me.matamor.commonapi.storage.identifier;

import me.matamor.commonapi.storage.database.DatabaseException;
import me.matamor.commonapi.storage.database.defaults.single.SimpleSingleSQLDatabaseManager;
import me.matamor.commonapi.utils.map.Callback;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.UUID;

public class SimpleIdentifierDatabase extends SimpleSingleSQLDatabaseManager<UUID, Identifier> implements IdentifierDatabase {

    public SimpleIdentifierDatabase(Plugin plugin) {
        this(plugin, false);
    }

    public SimpleIdentifierDatabase(Plugin plugin, boolean open) {
        super(plugin, "Identifier", 1.0, open);
    }

    @Override
    public void executeInsertQuery(PreparedStatement statement, Identifier data) throws SQLException {
        statement.setInt(1, data.getId());
        statement.setString(2, data.getUUID().toString());
        statement.setString(3, data.getName());

        statement.executeUpdate();
    }

    @Override
    public void createSelectQuery(PreparedStatement statement, UUID key) throws SQLException {
        statement.setString(1, key.toString());
    }

    @Override
    public Identifier deserialize(ResultSet resultSet, UUID uuid) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        return new SimpleIdentifier(this, id, uuid, name);
    }

    @Override
    public void executeRemoveQuery(PreparedStatement statement, UUID key) throws SQLException {
        statement.setString(1, key.toString());

        statement.executeUpdate();
    }

    @Override
    public Identifier loadOrCreate(UUID uuid, String name) throws SQLException {
        Identifier identifier = load(uuid);

        if (identifier == null) {
            try (Connection connection = getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(getQueries().getInsert(), Statement.RETURN_GENERATED_KEYS)) {
                    statement.setString(1, uuid.toString());
                    statement.setString(2, name);

                    statement.executeUpdate();

                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    generatedKeys.next();

                    identifier = new SimpleIdentifier(this, generatedKeys.getInt(1), uuid, name);
                }
            }
        }

        return identifier;
    }

    @Override
    public Identifier loadByName(String name) {
        try {
            try (Connection connection = getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(getQueries().getQuery("SelectByName"))) {
                    statement.setString(1, name);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            UUID uuid = UUID.fromString(resultSet.getString("uuid"));

                            return deserialize(resultSet, uuid);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't loadOrCreate Identifier by name!", e);
        }

        return null;
    }

    @Override
    public Identifier loadById(int id) {
        try {
            try (Connection connection = getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(getQueries().getQuery("SelectById"))) {
                    statement.setInt(1, id);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            UUID uuid = UUID.fromString(resultSet.getString("uuid"));

                            return deserialize(resultSet, uuid);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't loadOrCreate Identifier by id!", e);
        }

        return null;
    }

    @Override
    public void loadByIdAsync(int id, Callback<Identifier> callback) {
        runAsync(() -> {
            try {
                callback.done(loadById(id), null);
            } catch (Exception e) {
                callback.done(null, e);
            }
        });
    }

    @Override
    public boolean updateName(int id, String name) {
        try {
            try (Connection connection = getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(getQueries().getQuery("UpdateName"))) {
                    statement.setString(1, name);
                    statement.setInt(2, id);

                    statement.executeUpdate();

                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't loadOrCreate Identifier by id!", e);
        }
    }

    @Override
    public void updateNameAsync(int id, String name, Callback<Boolean> callback) {
        runAsync(() -> {
            try {
                boolean result = updateName(id, name);

                if (callback != null) {
                    callback.done(result, null);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.done(null, e);
                }
            }
        });
    }
}
