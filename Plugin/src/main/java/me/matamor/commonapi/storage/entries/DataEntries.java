package me.matamor.commonapi.storage.entries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.storage.DataHandler;
import me.matamor.commonapi.storage.DataProvider;
import me.matamor.commonapi.storage.data.PlayerData;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public class DataEntries {

    private final Map<Class<? extends DataEntry>, DataProvider<?>> providers = new LinkedHashMap<>();

    @Getter
    private final DataHandler plugin;

    public final void registerEntries(DataProvider<?>... entries) {
        registerEntries(false, entries);
    }

    public final void registerEntries(boolean register, DataProvider<?>... entries) {
        for (DataProvider<?> dataProvider : entries) {
            this.providers.put(dataProvider.getDataClass(), dataProvider);
        }

        if (register) {
            for (PlayerData playerData : this.plugin.getDataManager().getEntries()) {
                for (DataProvider<?> dataProvider : entries) {
                    if (!playerData.hasData(dataProvider.getDataClass())) {
                        playerData.registerData(dataProvider);
                    }
                }
            }
        }
    }

    public final void unregisterEntries(DataProvider<?>... entries) {
        unregisterEntries(false, entries);
    }

    public final void unregisterEntries(boolean unregister, DataProvider... entries) {
        for (DataProvider<?> dataProvider : entries) {
            this.providers.remove(dataProvider.getDataClass());
        }

        if (unregister) {
            for (PlayerData playerData : this.plugin.getDataManager().getEntries()) {
                for (DataProvider<?> dataProvider : entries) {
                    if (playerData.hasData(dataProvider.getDataClass())) {
                        playerData.unregister(dataProvider.getDataClass());
                    }
                }
            }
        }
    }

    public Collection<DataProvider<?>> getEntries() {
        return Collections.unmodifiableCollection(this.providers.values());
    }
}
