package me.matamor.commonapi.storage.identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class SimpleIdentifier implements Identifier {

    @Getter
    private final IdentifierDatabase databaseManager;

    @Getter
    private final int id;

    private UUID uuid;

    @Getter
    private String name;

    @Override
    public IdentifierDatabase getIdentifierDatabase() {
        return this.databaseManager;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public Player getPlayer() {
        return this.databaseManager.getPlugin().getServer().getPlayer(this.uuid);
    }

    @Override
    public void setUUID(UUID uuid) {
        if (!Objects.equals(this.uuid, uuid)) {

        }
    }

    @Override
    public void setName(String name) {
        if (!Objects.equals(this.name, name)) {
            try {
                this.databaseManager.updateName(this.id, name);
                this.name = name;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "(" + this.id + ", " + this.name + ", " + this.uuid.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (getClass() == object.getClass()) {
            return getId() == ((Identifier) object).getId();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
