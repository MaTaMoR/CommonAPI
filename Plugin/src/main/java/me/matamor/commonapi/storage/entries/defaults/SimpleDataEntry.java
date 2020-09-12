package me.matamor.commonapi.storage.entries.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.storage.DataEntryContainer;
import me.matamor.commonapi.storage.entries.DataEntry;
import me.matamor.commonapi.storage.identifier.Identifier;

@AllArgsConstructor
public abstract class SimpleDataEntry implements DataEntry {

    @Getter
    private DataEntryContainer<DataEntry> container;

    @Getter
    private final Identifier identifier;

}
