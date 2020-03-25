package me.matamor.commonapi.storage.data;
import me.matamor.commonapi.storage.StorageException;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.storage.identifier.SimpleIdentifier;
import me.matamor.commonapi.utils.map.Callback;

import java.util.Collection;
import java.util.UUID;

public interface DataManager {

    PlayerData load(Identifier identifier);

    PlayerData load(Identifier identifier, boolean cache);

    void loadAsync(Identifier identifier, Callback<PlayerData> callback);

    void loadAsync(Identifier identifier, boolean cache, Callback<PlayerData> callback);

    void loadAll();

    PlayerData getData(UUID uuid);

    PlayerData getData(String name);

    void unload(UUID uuid);

    void unload(PlayerData playerData);

    void unloadAsync(UUID uuid);

    void unloadAsync(PlayerData playerData);

    void unloadAll() throws StorageException;

    Collection<PlayerData> getEntries();

}
