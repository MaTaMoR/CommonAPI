package me.matamor.commonapi.storage.entries.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.storage.entries.DataEntry;
import me.matamor.commonapi.storage.entry.EntryDataStorage;
import me.matamor.commonapi.storage.identifier.SimpleIdentifier;
import me.matamor.commonapi.utils.map.Callback;
import org.bukkit.plugin.Plugin;

@AllArgsConstructor
public abstract class SimpleDataEntry implements DataEntry {

    @Getter
    private final Plugin plugin;

    @Getter
    private final SimpleIdentifier identifier;

    @Getter
    private final EntryDataStorage dataStorage;

    @Override
    public void save() {
        this.dataStorage.save(this);
    }

    @Override
    public void saveAsync(Callback<Boolean> callback) {
        this.dataStorage.saveAsync(this, callback);
    }
}
