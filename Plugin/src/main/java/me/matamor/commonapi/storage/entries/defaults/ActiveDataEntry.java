package me.matamor.commonapi.storage.entries.defaults;

import lombok.Getter;
import me.matamor.commonapi.storage.DataEntryContainer;
import me.matamor.commonapi.storage.entries.Active;
import me.matamor.commonapi.storage.entries.DataEntry;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.utils.Name;

public class ActiveDataEntry<T extends Name> extends ListDataEntry<T> implements Active<T> {

    @Getter
    private T active;

    public ActiveDataEntry(DataEntryContainer<DataEntry> container, Identifier identifier) {
        super(container, identifier);
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
