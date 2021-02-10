package me.matamor.commonapi.economy;

import lombok.Getter;
import me.matamor.commonapi.storage.identifier.Identifier;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleEconomyEntry implements EconomyEntry {

    private final Map<String, Double> entries = new ConcurrentHashMap<>();

    private final Set<PaymentNotification> notifications = Collections.synchronizedSet(new HashSet<>());

    @Getter
    private final EconomyPlugin plugin;

    @Getter
    private final Identifier identifier;

    private final boolean newAccount;

    public SimpleEconomyEntry(EconomyPlugin plugin, Identifier identifier) {
        this(plugin, identifier, true, null, null);
    }

    public SimpleEconomyEntry(EconomyPlugin plugin, Identifier identifier, boolean newAccount, Map<String, Double> entriesDefaults, Set<PaymentNotification> notificationsDefaults) {
        this.plugin = plugin;
        this.identifier = identifier;
        this.newAccount = newAccount;

        if (entriesDefaults != null) {
            this.entries.putAll(entriesDefaults);
        }

        if (notificationsDefaults != null) {
            this.notifications.addAll(notificationsDefaults);
        }
    }

    @Override
    public boolean isNewAccount() {
        return this.newAccount;
    }

    @Override
    public boolean hasAccount(String account) {
        return this.entries.containsKey(account);
    }

    @Override
    public double getBalance(String account) {
        return this.entries.getOrDefault(account, (double) 0);
    }

    @Override
    public double setBalance(String account, double balance) {
        double extra = 0;

        if (balance < this.plugin.getPluginConfig().minMoney)  {
            balance = this.plugin.getPluginConfig().minMoney;
        } else if (balance > this.plugin.getPluginConfig().maxMoney) {
            extra = balance - this.plugin.getPluginConfig().maxMoney;
            balance = this.plugin.getPluginConfig().maxMoney;
        }

        this.entries.put(account, balance);

        this.plugin.getDatabase().saveEconomyEntryAsync(this.identifier.getId(), account, balance);

        return extra;
    }

    @Override
    public double addBalance(String account, double balance) {
        return setBalance(account, getBalance(account) + balance);
    }

    @Override
    public void removeBalance(String account, double balance) {
        setBalance(account, getBalance(account) - balance);
    }

    @Override
    public Set<Entry<String, Double>> getEntries() {
        return Collections.unmodifiableSet(this.entries.entrySet());
    }

    @Override
    public void addNotification(PaymentNotification notification) {
        if (this.notifications.add(notification)) {
            this.plugin.getDatabase().saveNotificationEntryAsync(getIdentifier().getId(), notification);
        }
    }

    @Override
    public boolean hasNotifications() {
        return this.notifications.size() > 0;
    }

    @Override
    public boolean hasNotification(PaymentNotification notification) {
        return this.notifications.contains(notification);
    }

    @Override
    public void deleteNotification(PaymentNotification notification) {
        if (this.notifications.remove(notification)) {
            this.plugin.getDatabase().deleteNotificationEntryAsync(getIdentifier().getId(), notification);
        }
    }

    @Override
    public void clearNotifications() {
        this.notifications.clear();

        this.plugin.getDatabase().deleteNotificationsAsync(getIdentifier().getId());
    }

    @Override
    public Set<PaymentNotification> getNotifications() {
        return this.notifications;
    }
}
