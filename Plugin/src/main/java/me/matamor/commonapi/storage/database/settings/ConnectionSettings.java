package me.matamor.commonapi.storage.database.settings;

import lombok.Getter;
import me.matamor.commonapi.storage.database.DatabaseException;
import me.matamor.commonapi.utils.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConnectionSettings {

    private final Map<ConnectionSettingsType, Object> values = new LinkedHashMap<>();

    @Getter
    private final String name;

    @Getter
    private final ConnectionSettingsManager settingsManager;

    public ConnectionSettings(String name, ConnectionSettingsManager settingsManager) {
        this(name, settingsManager, null);
    }

    public ConnectionSettings(String name, ConnectionSettingsManager settingsManager, Map<ConnectionSettingsType, Object> defaults) {
        this.name = name;
        this.settingsManager = settingsManager;

        if (defaults != null) {
            this.values.putAll(defaults);
        }
    }

    public void set(ConnectionSettingsType settingsType, Object object) {
        Validate.notNull(settingsType, "ConnectionSettingsType can't be null!");
        Validate.notNull(object, "Object can't be null!");
        Validate.isTrue(settingsType.isValid(object), "Object must be valid!");

        this.values.put(settingsType, object);
    }

    public boolean contains(ConnectionSettingsType key) {
        return this.values.containsKey(key);
    }

    public Object getUnchecked(ConnectionSettingsType settingsType) {
        Validate.notNull(settingsType, "ConnectionSettingsType can't be null!");

        Object object = this.values.get(settingsType);
        if (object == null && this.settingsManager.getDefaultSettings() != null) {
            return this.settingsManager.getDefaultSettings().getUnchecked(settingsType);
        }

        return object;
    }

    public Object get(ConnectionSettingsType settingsType) throws DatabaseException {
        Validate.notNull(settingsType, "ConnectionSettingsType can't be null!");

        Object object = this.values.get(settingsType);
        if (object == null) {
            if (this.settingsManager.getDefaultSettings() != null) {
                return this.settingsManager.getDefaultSettings().get(settingsType);
            } else {
                throw new DatabaseException("Missing value, key: " + settingsType.name());
            }
        }

        return object;
    }

    public Map<ConnectionSettingsType, Object> getSettings() {
        return this.values;
    }

    public boolean isEmpty() {
        return this.values.isEmpty();
    }
}
