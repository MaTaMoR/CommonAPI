package me.matamor.commonapi.scoreboard.nms;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface NMSViewable {

    /*
        There is a huge problem with this, this is meant to work with a Collection of Players
        so when you use the method 'show' it shows to all the Players in the Collection
        Same happens with the other methods 'update' and 'hide', the problem is when you use 'update'
        on a Player that wasn't there when 'show' was used
     */

    /**
     * Shows self
     */

    void show();

    /**
     * Shows self to the target Player
     * @param player the target Player, can't be null!
     */

    void show(@NotNull Player player);

    default void show(@NotNull Collection<Player> players) {
        players.forEach(this::show);
    }

    /**
     * Updates self
     */

    void update();

    /**
     * Updates self to the target Player
     * @param player the target Player, can't be null!
     */

    void update(@NotNull Player player);

    default void update(@NotNull Collection<Player> players) {
        players.forEach(this::update);
    }

    /**
     * Hides self
     */

    void hide();

    /**
     * Hides self to the target Player
     * @param player the target Player, can't be null!
     */

    void hide(@NotNull Player player);

    default void hide(@NotNull Collection<Player> players) {
        players.forEach(this::hide);
    }
}
