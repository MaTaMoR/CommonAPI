package me.matamor.commonapi.nms.v1_8_R3.scoreboard;

import lombok.Getter;
import me.matamor.commonapi.scoreboard.nms.NMSObjective;
import me.matamor.commonapi.scoreboard.nms.NMSScoreboard;
import me.matamor.commonapi.scoreboard.nms.NMSTeam;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NMSScoreboardImpl implements NMSScoreboard {

    @Getter
    private final NMSObjective[] objectives;

    private final Map<String, NMSTeam> teams;

    @Getter
    private final List<Player> viewers;

    public NMSScoreboardImpl() {
        this.objectives = new NMSObjective[DisplaySlot.values().length];
        this.teams = new LinkedHashMap<>();
        this.viewers = new ArrayList<>();
    }

    @Override
    public @NotNull NMSObjective getObjective(@NotNull DisplaySlot displaySlot) {
        NMSObjective objective = this.objectives[displaySlot.ordinal()];
        if (objective == null) {
            objective = new NMSObjectiveImpl(this, displaySlot);
            this.objectives[displaySlot.ordinal()] = objective;
        }

        return objective;
    }

    @Override
    public @NotNull NMSTeam registerNewTeam(@NotNull String name) {
        return this.teams.computeIfAbsent(name, k -> new NMSTeamImpl(this, k));
    }

    @Override
    public @Nullable NMSTeam getTeam(@NotNull String name) {
        return this.teams.get(name);
    }

    @Override
    public @NotNull Collection<NMSTeam> getTeams() {
        return Collections.unmodifiableCollection(this.teams.values());
    }

    @Override
    public void show() {
        show(this.viewers);
    }

    @Override
    public void show(@NotNull Player viewer) {
        if (!this.viewers.contains(viewer)) {
            this.viewers.add(viewer);

            for (NMSObjective objective : this.objectives) {
                if (objective != null) {
                    objective.show(viewer);
                }
            }

            this.teams.values().forEach(e -> e.show(viewer));
        }
    }

    @Override
    public void update() {
        update(this.viewers);
    }

    @Override
    public void update(@NotNull Player viewer) {
        if (this.viewers.contains(viewer)) {
            for (NMSObjective objective : this.objectives) {
                if (objective != null) {
                    objective.update(viewer);
                }
            }

            this.teams.values().forEach(e -> e.update(viewer));
        }
    }

    @Override
    public void hide() {
        hide(this.viewers);
    }

    @Override
    public void hide(@NotNull Player viewer) {
        if (this.viewers.remove(viewer)) {
            for (NMSObjective objective : this.objectives) {
                if (objective != null) {
                    objective.hide(viewer);
                }
            }

            this.teams.values().forEach(e -> e.hide(viewer));
        }
    }
}
