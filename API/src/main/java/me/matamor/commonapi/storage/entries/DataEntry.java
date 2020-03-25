package me.matamor.commonapi.storage.entries;

import me.matamor.commonapi.storage.DataStorage;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.utils.map.Callback;
import org.bukkit.plugin.Plugin;

public interface DataEntry {

    Plugin getPlugin();

    Identifier getIdentifier();

    DataStorage getDataStorage();

    void save();

    void saveAsync(Callback<Boolean> callback);

}
