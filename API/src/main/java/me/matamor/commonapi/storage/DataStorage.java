package me.matamor.commonapi.storage;

import org.bukkit.plugin.Plugin;

import java.util.concurrent.ExecutorService;

public interface DataStorage {

    Plugin getPlugin();

    ExecutorService getExecutor();

    default void runAsync(Runnable runnable) {
        getExecutor().submit(runnable);
    }
}
