package me.matamor.commonapi.storage.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.matamor.commonapi.storage.DataHandler;
import me.matamor.commonapi.storage.DataProvider;
import me.matamor.commonapi.storage.StorageException;
import me.matamor.commonapi.storage.entries.DataEntry;
import me.matamor.commonapi.storage.entries.RegisteredData;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class SimplePlayerData implements PlayerData {

    private final Map<Class<?>, RegisteredData> dataEntries = new ConcurrentHashMap<>();
    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();
    private final Map<String, Object> storedData = new ConcurrentHashMap<>();

    @Getter
    private final DataHandler handler;

    @Getter
    private final Identifier identifier;

    @Override
    public UUID getUUID() {
        return this.identifier.getUUID();
    }

    @Override
    public Player getPlayer() {
        return this.identifier.getPlayer();
    }

    @Override
    public <T extends DataEntry> T registerData(DataProvider<T> dataProvider) {
        Validate.isFalse(this.dataEntries.containsKey(dataProvider.getDataClass()), "The DataValue " + dataProvider.getDataClass().getName() + " is already registered");

        T data = dataProvider.getStorage().load(this.identifier);
        if (data == null) {
            data = dataProvider.newInstance(this);
        }

        this.dataEntries.put(dataProvider.getDataClass(), new RegisteredData(this, data, dataProvider));

        return data;
    }

    @Override
    public Object registerInstance(Class<?> clazz, Object instance) {
        Validate.isTrue(clazz.isAssignableFrom(instance.getClass()), "The provided object isn't instance of the class '" + clazz.getName() + "'");
        Validate.isFalse(this.instances.containsKey(clazz), "The Class " + clazz.getName() + " is already registered");

        this.instances.put(clazz, instance);

        return instance;
    }

    @Override
    public <T> T setStoredData(String key, T data) {
        Validate.isFalse(this.storedData.containsKey(key), "Data already stored with the key " + key);

        this.storedData.put(key, data);

        return data;
    }

    @Override
    public boolean hasData(Class<?> clazz) {
        return this.dataEntries.containsKey(clazz);
    }

    @Override
    public boolean hasInstance(Class<?> clazz) {
        return this.instances.containsKey(clazz);
    }

    @Override
    public boolean hasStoredData(String key) {
        return this.storedData.containsKey(key);
    }

    @Override
    public <T extends DataEntry> T getData(Class<T> clazz) {
        RegisteredData data = this.dataEntries.get(clazz);
        if (data == null) {
            return null;
        } else if (clazz.isAssignableFrom(data.getDataEntry().getClass())) {
            return (T) data.getDataEntry();
        } else {
            throw new RuntimeException("The Data " + data.getDataEntry().getClass().getName() + " isn't instance of " + clazz.getName());
        }
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        Object data = this.instances.get(clazz);
        if (data == null) {
            return null;
        } else if (clazz.isAssignableFrom(data.getClass())) {
            return (T) data;
        } else {
            throw new RuntimeException("The Object " + data.getClass().getName() + " isn't instance of " + clazz.getName());
        }
    }

    @Override
    public Object getStoredData(String key) {
        return this.storedData.get(key);
    }

    @Override
    public <T> T getStoredData(String key, Class<T> clazz) {
        Object data = this.storedData.get(key);
        if (data == null) {
            return null;
        } else if (clazz.isAssignableFrom(data.getClass())) {
            return (T) data;
        } else {
            throw new RuntimeException("The Object " + data.getClass().getName() + " isn't instance of " + clazz.getName());
        }
    }

    @Override
    public RegisteredData getRegisteredData(Class<?> clazz) {
        return this.dataEntries.get(clazz);
    }

    @Override
    public void unregister(Class<?> clazz) {
        RegisteredData registeredData = this.dataEntries.get(clazz);
        if (registeredData == null) return;

        registeredData.save();

        this.dataEntries.remove(clazz);
    }

    @Override
    public void unregisterInstance(Class<?> clazz) {
        this.instances.remove(clazz);
    }

    @Override
    public void removeStoredData(String key) {
        this.storedData.remove(key);
    }

    @Override
    public void removeData(Class<?> clazz) {
        this.dataEntries.remove(clazz);
    }

    @Override
    public void purgeData(Class<?> clazz) {
        RegisteredData dataEntry = this.dataEntries.get(clazz);
        if (dataEntry == null) return;

        this.dataEntries.remove(clazz);

        try {
            dataEntry.getDataProvider().getStorage().delete(getIdentifier());
        } catch (StorageException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveData() throws StorageException {
        for (RegisteredData registeredData : this.dataEntries.values()) {
            registeredData.save();
        }
    }

    @Override
    public void saveDataAsync() {
        this.dataEntries.values().forEach(RegisteredData::saveAsync);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof PlayerData) {
            PlayerData other = (PlayerData) object;
            return Objects.equals(getIdentifier(), other.getIdentifier());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIdentifier());
    }
}
