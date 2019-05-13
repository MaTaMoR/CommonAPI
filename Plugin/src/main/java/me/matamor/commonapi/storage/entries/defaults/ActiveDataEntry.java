package me.matamor.commonapi.storage.entries.defaults;

import lombok.Getter;
import me.matamor.commonapi.storage.entries.Active;
import me.matamor.commonapi.storage.entry.EntryDataStorage;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.utils.Name;
import org.bukkit.plugin.Plugin;

public class ActiveDataEntry<T extends Name> extends ListDataEntry<T> implements Active<T> {

    @Getter
    private T active;

    public ActiveDataEntry(Plugin plugin, Identifier identifier, EntryDataStorage dataStorage) {
        super(plugin, identifier, dataStorage);
    }

    @Override
    public void setActive(T active) {
        this.active = active;

        if (hasActive()) {
            add(active);
        }
    }

    @Override
    public boolean hasActive() {
        return this.active != null;
    }
}
