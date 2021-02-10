package me.matamor.commonapi.economy.database;

import me.matamor.commonapi.CommonAPI;
import me.matamor.commonapi.economy.EconomyEntry;
import me.matamor.commonapi.economy.EconomyModule;
import me.matamor.commonapi.economy.PaymentNotification;
import me.matamor.commonapi.economy.SimpleEconomyEntry;
import me.matamor.commonapi.economy.commands.BalanceTopCommand;
import me.matamor.commonapi.storage.database.DatabaseException;
import me.matamor.commonapi.storage.database.defaults.single.SimpleMultiSQLDatabaseManager;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.utils.map.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.logging.Level;

public class EconomySQLDatabase extends SimpleMultiSQLDatabaseManager<Identifier, EconomyEntry> {

    public EconomySQLDatabase(EconomyModule plugin) {
        super(plugin, "Economy", 1.0);
    }

    @Override
    public EconomyModule getPlugin() {
        return (EconomyModule) super.getPlugin();
    }

    @Override
    public Collection<String> getCreateQueries() {
        return Arrays.asList(getQueries().getCreate("Economy"), getQueries().getCreate("Notifications"));
    }

    @Override
    public void saveMulti(EconomyEntry data, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(getQueries().getInsert("Economy"))) {
            for (Entry<String, Double> entry : data.getEntries()) {
                statement.setInt(1, data.getIdentifier().getId());
                statement.setString(2, entry.getKey());
                statement.setDouble(3, entry.getValue());

                statement.addBatch();
            }

            statement.executeBatch();
        }

        try (PreparedStatement statement = connection.prepareStatement(getQueries().getInsert("Notifications"))) {
            for (PaymentNotification notification : data.getNotifications()) {
                statement.setInt(1, data.getIdentifier().getId());
                statement.setString(2, notification.getName());
                statement.setLong(3, notification.getDate());
                statement.setDouble(4, notification.getAmount());

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    @Override
    public EconomyEntry loadMulti(Identifier key, Connection connection) throws SQLException {
        Map<String, Double> entries = new LinkedHashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(getQueries().getSelect("Economy"))) {
            statement.setInt(1, key.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    entries.put(resultSet.getString("account"), resultSet.getDouble("balance"));
                }
            }
        }

        Set<PaymentNotification> notifications = new HashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(getQueries().getSelect("Notifications"))) {
            statement.setInt(1, key.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notifications.add(new PaymentNotification(resultSet.getString("name"), resultSet.getLong("date"), resultSet.getDouble("amount")));
                }
            }
        }

        return new SimpleEconomyEntry(getPlugin(), key, (entries.isEmpty()), entries, notifications);
    }

    @Override
    public void deleteMulti(Identifier key, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(getQueries().getDelete("Economy"))) {
            statement.setInt(1, key.getId());

            statement.executeUpdate();
        }

        try (PreparedStatement statement = connection.prepareStatement(getQueries().getDelete("Notifications"))) {
            statement.setInt(1, key.getId());

            statement.executeUpdate();
        }
    }

    public void saveEconomyEntry(int id, String account, double balance) {
        try {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(getQueries().getInsert("Economy"))) {
                statement.setInt(1, id);
                statement.setString(2, account);
                statement.setDouble(3, balance);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            getPlugin().getLogger().log(Level.SEVERE, "Couldn't save economy entry: " + id, e);
        }
    }

    public void saveEconomyEntryAsync(int id, String account, double balance) {
        runAsync(() -> saveEconomyEntry(id, account, balance));
    }

    public void deleteEconomyEntry(int id, String account) {
        try {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(getQueries().getDelete("EconomyEntry"))) {
                statement.setInt(1, id);
                statement.setString(2, account);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            getPlugin().getLogger().log(Level.SEVERE, "Couldn't remove economy entry: " + id, e);
        }
    }

    public void deleteEconomyEntryAsync(int id, String account) {
        runAsync(() -> deleteEconomyEntry(id, account));
    }

    public void saveNotificationEntry(int id, PaymentNotification notification) {
        try {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(getQueries().getInsert("Notifications"))) {
                statement.setInt(1, id);
                statement.setString(2, notification.getName());
                statement.setLong(3, notification.getDate());
                statement.setDouble(4, notification.getAmount());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            getPlugin().getLogger().log(Level.SEVERE, "Couldn't save entry: " + id, e);
        }
    }

    public void saveNotificationEntryAsync(int id, PaymentNotification notification) {
        runAsync(() -> saveNotificationEntry(id, notification));
    }

    public void deleteNotificationEntry(int id, PaymentNotification notification) {
        try {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(getQueries().getDelete("NotificationEntry"))) {
                statement.setInt(1, id);
                statement.setLong(2, notification.getDate());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            getPlugin().getLogger().log(Level.SEVERE, "Couldn't save entry: " + id, e);
        }
    }

    public void deleteNotificationEntryAsync(int id, PaymentNotification notification) {
        runAsync(() -> deleteNotificationEntry(id, notification));
    }

    public void deleteNotifications(int id) {
        try {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(getQueries().getDelete("Notifications"))) {
                statement.setInt(1, id);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            getPlugin().getLogger().log(Level.SEVERE, "Couldn't save entry: " + id, e);
        }
    }

    public void deleteNotificationsAsync(int id) {
        runAsync(() -> deleteNotifications(id));
    }

    public boolean accountExists(int id, String account) {
        try {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(getQueries().getQuery("AccountExists"))) {
                statement.setInt(1, id);
                statement.setString(2, account);

                return statement.executeQuery().next();
            }
        } catch (SQLException e) {
            getPlugin().getLogger().log(Level.SEVERE, "Couldn't remove entry: " + id, e);
        }

        return false;
    }

    public void loadTopBalance(Consumer<BalanceTopCommand.BalanceTopEntry> onEntry, Callback<Boolean> onEnd) {
        try {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(getQueries().getQuery("Top"))) {
                statement.setString(1, getPlugin().getPluginConfig().vaultAccount);

                try (ResultSet resultSet = statement.executeQuery()) {
                    int position = 0;

                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        double balance = resultSet.getDouble("balance");

                        Identifier identifier = CommonAPI.getInstance().getIdentifierManager().getIdentifier(id);
                        if (identifier == null) {
                            identifier = CommonAPI.getInstance().getIdentifierManager().load(id);
                        }

                        if (identifier != null) {
                            onEntry.accept(new BalanceTopCommand.BalanceTopEntry(++position, identifier.getName(), balance));
                        }
                    }

                    onEnd.done(true, null);
                }
            }
        } catch (SQLException e) {
            onEnd.done(false, e);
        }
    }

    public void loadTopBalanceAsync(Consumer<BalanceTopCommand.BalanceTopEntry> onEntry, Callback<Boolean> onEnd) {
        runAsync(() -> loadTopBalance(onEntry, onEnd));
    }

    public int countEntries() {
        try {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(getQueries().getQuery("CountEntries"))) {
                statement.setString(1, getPlugin().getPluginConfig().vaultAccount);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    } else {
                        return 0;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't count entries!", e);
        }
    }
}
