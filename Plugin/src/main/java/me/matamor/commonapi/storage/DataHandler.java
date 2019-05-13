package me.matamor.commonapi.storage;

import me.matamor.commonapi.storage.data.DataManager;
import me.matamor.commonapi.storage.entries.DataEntries;
import me.matamor.commonapi.storage.entries.DataStorageManager;
import me.matamor.commonapi.storage.identifier.IdentifierManager;
import org.bukkit.plugin.Plugin;

public interface DataHandler {

    Plugin getPlugin();

    DataEntries getDataEntries();

    InstanceProviderManager getInstanceProviderManager();

    DataStorageManager getStorageManager();

    IdentifierManager getIdentifierManager();

    DataManager getDataManager();

}

