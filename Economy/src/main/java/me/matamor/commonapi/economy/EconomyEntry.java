package me.matamor.commonapi.economy;

import me.matamor.commonapi.storage.identifier.SimpleIdentifier;

import java.util.Map.Entry;
import java.util.Set;

public interface EconomyEntry {

    EconomyPlugin getPlugin();

    SimpleIdentifier getIdentifier();

    boolean hasAccount(String account);

    double getBalance(String account);

    double setBalance(String account, double balance);

    double addBalance(String account, double balance);

    void removeBalance(String account, double balance);

    Set<Entry<String, Double>> getEntries();

    void addNotification(PaymentNotification notification);

    boolean hasNotifications();

    boolean hasNotification(PaymentNotification notification);

    void deleteNotification(PaymentNotification notification);

    void clearNotifications();

    Set<PaymentNotification> getNotifications();

}
