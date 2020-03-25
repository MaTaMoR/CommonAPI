package me.matamor.commonapi.storage.identifier;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface Identifier {

    IdentifierDatabase getIdentifierDatabase();

    int getId();

    UUID getUUID();

    String getName();

    Player getPlayer();

    void setUUID(UUID uuid);

    void setName(String name);

}
