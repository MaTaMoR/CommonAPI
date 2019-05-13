package me.matamor.commonapi.storage.identifier;

import me.matamor.commonapi.storage.StorageException;
import me.matamor.commonapi.storage.identifier.listener.IdentifierListener;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface IdentifierManager {

    Identifier load(int id);

    Identifier load(String name);

    Identifier load(UUID uuid);

    Identifier loadOrCreate(UUID uuid, String name);

    void register(Identifier identifier);

    void loadAll() throws StorageException;

    Identifier getIdentifier(int id);

    Identifier getIdentifier(UUID uuid);

    Identifier getIdentifier(String name);

    Collection<Identifier> getIdentifiers();

    Identifier remove(UUID uuid);

    void unloadAll();

    void registerListener(String name, IdentifierListener listener);

    IdentifierListener getListener(String name);

    void removeListener(String name);

    Collection<Map.Entry<String, IdentifierListener>> getListeners();

}
