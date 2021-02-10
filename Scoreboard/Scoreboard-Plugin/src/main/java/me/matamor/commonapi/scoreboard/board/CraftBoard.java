package me.matamor.commonapi.scoreboard.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.scoreboard.ScoreboardModule;
import me.matamor.commonapi.scoreboard.nms.NMSObjective;
import me.matamor.commonapi.scoreboard.nms.NMSScore;
import me.matamor.commonapi.scoreboard.nms.NMSScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class CraftBoard implements Board {

    private static int idCount;

    @Getter
    private final ScoreboardModule plugin;

    @Getter
    private final BoardTemplate template;

    @Getter
    private final int id;

    @Getter
    private BoardLine displayName;

    private final Map<Integer, BoardLine> lines = new LinkedHashMap<>();

    private final Map<UUID, PlayerBoardData> viewers = new LinkedHashMap<>();

    public CraftBoard(@NotNull ScoreboardModule plugin) {
        this(plugin,null);
    }

    public CraftBoard(@NotNull ScoreboardModule plugin, @Nullable BoardTemplate template) {
        this.plugin = plugin;
        this.template = template;
        this.id = idCount++;
    }

    @Override
    public boolean hasTemplate() {
        return this.template != null;
    }

    @Override
    public <T extends BoardLine> @Nullable T setDisplayName(T displayName) {
        this.displayName = displayName;

        updateDisplayName();

        return displayName;
    }

    private void cleanUp() {
        this.viewers.entrySet().removeIf(e -> !e.getValue().getPlayer().isOnline());
    }

    private void updateDisplayName() {
        cleanUp();

        if (this.viewers.size() > 0) {
            for (PlayerBoardData boardData : this.viewers.values()) {
                NMSObjective objective = boardData.getObjective();

                if (this.displayName == null) {
                    if (!objective.getDisplayName().isEmpty()) {
                        objective.setDisplayName("");
                    }
                } else {
                    String text = this.displayName.getText(boardData.getPlayer());
                    if (!text.equals(objective.getDisplayName())) {
                        objective.setDisplayName(text);
                    }
                }
            }
        }
    }

    private void updateLine(int row) {
        BoardLine line = this.lines.get(row);
        if (line == null) {
            return;
        }

        cleanUp();

        for (PlayerBoardData boardData : this.viewers.values()) {
            String text = line.getText(boardData.getPlayer());
            if (text.length() > 40) {
                text = text.substring(0, 40);
            }

            //Using a boolean to prevent ConcurrentModificationException
            NMSScore score = null;

            for (NMSScore nmsScore : boardData.getObjective().getScores()) {
                //Check if the Score is the same
                if (nmsScore.getScore() == row) {
                    //The entry is already in the scoreboard
                    if (nmsScore.getEntry().equals(text)) {
                        return;
                    }

                    //Don't directly remove the entry, assign it to a variable to remove it later.
                    score = nmsScore;
                    break;
                }
            }

            if (score != null) {
                boardData.getObjective().removeScore(score.getEntry());
            }

            boardData.getObjective().getScore(text).setScore(row);
        }
    }

    public void updateLines() {
        for (int row : this.lines.keySet()) {
            updateLine(row);
        }

        cleanUp();

        for (PlayerBoardData boardData : this.viewers.values()) {
            Set<NMSScore> toRemove = new HashSet<>();

            //Remove all entries that aren't registered
            for (NMSScore nmsScore : boardData.getObjective().getScores()) {
                if (this.lines.get(nmsScore.getScore()) == null) {
                    //Don't directly remove the entry, add it to a list to remove it later.
                    toRemove.add(nmsScore);
                }
            }

            //Remove the entries.
            toRemove.forEach(e -> boardData.getObjective().removeScore(e.getEntry()));
        }
    }

    public void removeEntry(int row) {
        if (row > 15 || row < 0) {
            return;
        }

        for (PlayerBoardData boardData : this.viewers.values()) {
            NMSScore score = null;

            for (NMSScore nmsScore : boardData.getObjective().getScores()) {
                if (nmsScore.getScore() == row) {
                    //Don't directly remove the entry, assign it to a variable to remove it later.
                    score = nmsScore;
                    break;
                }
            }

            if (score != null) {
                boardData.getObjective().removeScore(score.getEntry());
            }

        }

        this.lines.remove(row);
    }


    @Override
    public <T extends BoardLine> @Nullable T setLine(int row, T entry) {
        this.lines.put(row, entry);

        updateLine(row);

        return entry;
    }

    @Override
    public @Nullable BoardLine getLine(int row) {
        return this.lines.get(row);
    }

    @Override
    public void show() {
         //There isn't much to do here, we can't show the scoreboard to the viewers because it's already shown!
    }

    @Override
    public void show(@NotNull Player viewer) {
        createScoreboard(viewer);
    }

    private PlayerBoardData createScoreboard(Player viewer) {
        return this.viewers.computeIfAbsent(viewer.getUniqueId(), (key) -> {
            NMSScoreboard scoreboard = this.plugin.getScoreboardController().createScoreboard();
            NMSObjective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

            objective.setDisplayName(this.displayName.getText(viewer));

            this.lines.forEach((k, v) -> objective.getScore(v.getText(viewer)).setScore(k));

            scoreboard.show(viewer);

            return new PlayerBoardData(viewer, scoreboard, objective);
        });
    }

    @Override
    public void show(@NotNull Collection<Player> viewers) {
        viewers.forEach(this::show);
    }

    @Override
    public void update() {
        update(getViewers());
    }

    @Override
    public void update(@NotNull Player viewer) {
        updateDisplayName();
        updateLines();
    }

    @Override
    public void hide() {
        this.viewers.values().forEach(e -> e.getScoreboard().hide());
        this.viewers.clear();
    }

    @Override
    public void hide(@NotNull Player viewer) {
        PlayerBoardData playerBoardData = this.viewers.remove(viewer.getUniqueId());
        if (playerBoardData != null) {
            playerBoardData.getScoreboard().hide();
        }
    }

    @Override
    public void hide(@NotNull Collection<Player> viewers) {
        viewers.forEach(this::hide);
    }

    @Override
    public @NotNull Collection<Player> getViewers() {
        return this.viewers.values().stream().map(PlayerBoardData::getPlayer).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        hide();
    }

    @AllArgsConstructor
    private static class PlayerBoardData {

        @Getter
        private final Player player;

        @Getter
        private final NMSScoreboard scoreboard;

        @Getter
        private final NMSObjective objective;

    }
}
