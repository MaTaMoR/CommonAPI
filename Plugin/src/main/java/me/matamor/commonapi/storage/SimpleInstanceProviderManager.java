package me.matamor.commonapi.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.storage.data.PlayerData;
import me.matamor.commonapi.utils.Validate;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
public class SimpleInstanceProviderManager implements InstanceProviderManager {

    private final Map<Class<?>, InstanceProvider<?>> entries = new ConcurrentHashMap<>();

    @Getter
    private final DataHandler plugin;

    @Override
    public final <T> void registerProvider(Class<T> clazz, InstanceProvider<T> provider) {
        registerProvider(clazz, provider, false);
    }

    @Override
    public final <T> void registerProvider(Class<T> clazz, InstanceProvider<T> provider, boolean register) {
        Validate.isFalse(this.entries.containsKey(clazz), "A provider for the class '" + clazz.getName() + "' is already registered!");

        this.entries.put(clazz, provider);

        if (register) {
            for (PlayerData playerData : this.plugin.getDataManager().getEntries()) {
                if (!playerData.hasInstance(clazz)) {
                    playerData.registerInstance(clazz, provider.newInstance(playerData));
                }
            }
        }
    }

    @Override
    public final void unregisterProvider(Class<?> clazz) {
        unregisterProvider(clazz, false);
    }

    @Override
    public final void unregisterProvider(Class<?> clazz, boolean unregister) {
        this.entries.remove(clazz);

        if (unregister) {
            for (PlayerData playerData : this.plugin.getDataManager().getEntries()) {
                playerData.unregisterInstance(clazz);
            }
        }
    }

    @Override
    public Set<Entry<Class<?>, InstanceProvider<?>>> getEntries() {
        return Collections.unmodifiableSet(this.entries.entrySet());
    }
}
