package me.matamor.commonapi.economy;

import me.matamor.commonapi.CommonAPI;
import me.matamor.commonapi.storage.identifier.SimpleIdentifier;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public interface Economy {

    Plugin getPlugin();

    EconomyEntry load(SimpleIdentifier identifier);

    EconomyEntry load(SimpleIdentifier identifier, boolean cache);

    void loadAll();

    EconomyEntry getEntry(UUID uuid);

    EconomyEntry getEntry(String name);

    void remove(UUID uuid);

    void unload(UUID uuid);

    void unloadAll();

    default EconomyEntry getEntryOffline(String name) {
        EconomyEntry economyEntry = getEntry(name);
        if (economyEntry == null) {
            //Load first from memory
            SimpleIdentifier identifier = CommonAPI.getInstance().getIdentifierManager().getIdentifier(name);

            //If not loaded then try to directly loadOrCreate from database
            if (identifier == null) {
                identifier = CommonAPI.getInstance().getIdentifierManager().load(name);
            }

            if (identifier != null) {
                //Load entry using Identifier, this will create one if it doesn't exist, is not kept in memory!
                economyEntry = load(identifier, true);
            }
        }

        return economyEntry;
    }

    default EconomyEntry getEntryOffline(UUID uuid, String name) {
        EconomyEntry economyEntry = getEntry(uuid);
        if (economyEntry == null) {
            //Load first from memory
            SimpleIdentifier identifier = CommonAPI.getInstance().getIdentifierManager().getIdentifier(uuid);

            //If not loaded then try to directly loadOrCreate from database
            if (identifier == null) {
                identifier = CommonAPI.getInstance().getIdentifierManager().load(uuid);
            }

            if (identifier != null) {
                //Load entry using Identifier, this will create one if it doesn't exist, is not kept in memory!
                economyEntry = load(identifier, true);
            }
        }

        return economyEntry;
    }
}
