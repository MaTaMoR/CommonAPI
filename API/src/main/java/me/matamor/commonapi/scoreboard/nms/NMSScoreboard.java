package me.matamor.commonapi.scoreboard.nms;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface NMSScoreboard extends NMSViewable {

    /**
     * Gets the Objective of the target DisplaySlot
     * @param displaySlot the target DisplaySlot, can't be null!
     * @return the Objective, can't be null!
     */

    @NotNull
    NMSObjective getObjective(@NotNull DisplaySlot displaySlot);

    /**
     * Registers a Team with the target Name
     * @param name the target Name, can't be null!
     * @return the Team with the target Name, can't ne null!
     */

    @NotNull
    NMSTeam registerNewTeam(@NotNull String name);

    /**
     * Gets the Team with the target Name
     * @param name the target Name, can't be null!
     * @return the Team with the target Name, can be null!
     */

    @Nullable
    NMSTeam getTeam(@NotNull String name);

    /**
     * Gets the Team
     * @return a Collection of the Teams, can't be null!
     */

    @NotNull
    Collection<NMSTeam> getTeams();

    /**
     *
     */

    Collection<Player> getViewers();

}
