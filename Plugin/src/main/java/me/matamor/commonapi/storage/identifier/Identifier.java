package me.matamor.commonapi.storage.identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class Identifier {

    @Getter
    private final IdentifierSingleDatabaseManager databaseManager;

    @Getter
    private final int id;

    private UUID uuid;

    @Getter
    private String name;

    public UUID getUUID() {
        return this.uuid;
    }

    public Player getPlayer() {
        return this.databaseManager.getPlugin().getServer().getPlayer(this.uuid);
    }

    public void setUUID(UUID uuid) {
        if (!Objects.equals(this.uuid, uuid)) {

        }
    }

    public void setName(String name) {
        if (!Objects.equals(this.name, name)) {
            boolean result = this.databaseManager.updateName(this.id, name);
            if (result) {
                this.name = name;
            }
        }
    }

    @Override
    public String toString() {
        return "(" + this.id + ", " + this.name + ", " + this.uuid.toString() + ")";
    }
}
