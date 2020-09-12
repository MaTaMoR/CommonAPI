package me.matamor.commonapi.storage.entries;

import me.matamor.commonapi.storage.DataEntryContainer;
import me.matamor.commonapi.storage.identifier.Identifier;

public interface DataEntry {

    DataEntryContainer<DataEntry> getContainer();

    Identifier getIdentifier();

}
