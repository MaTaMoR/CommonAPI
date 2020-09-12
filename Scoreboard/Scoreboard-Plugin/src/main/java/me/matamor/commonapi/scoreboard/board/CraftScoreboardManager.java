package me.matamor.commonapi.scoreboard.board;

import lombok.Getter;
import me.matamor.commonapi.scoreboard.ScoreboardManager;
import me.matamor.commonapi.scoreboard.ScoreboardModule;
import me.matamor.commonapi.utils.BukkitTaskHandler;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class CraftScoreboardManager implements ScoreboardManager {

    @Getter
    private final ScoreboardModule plugin;

    private final List<Board> boards;

    private final List<UpdatingBoard> updatingBoards;

    @Getter
    private final BukkitTaskHandler taskHandler;

    public CraftScoreboardManager(ScoreboardModule plugin) {
        this.plugin = plugin;
        this.boards = new ArrayList<>();
        this.updatingBoards = new ArrayList<>();

        this.taskHandler = new BukkitTaskHandler() {
            @Override
            public BukkitTask createTask() {
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        long now = System.currentTimeMillis();

                        updatingBoards.removeIf(e -> !e.update(now));
                    }
                }.runTaskTimer(plugin, 0, 1);
            }
        };
    }

    @Override
    public Board createBoard() {
        Board board = new CraftBoard(this.plugin);

        this.boards.add(board);

        return board;
    }

    @Override
    public UpdatingBoard createUpdatingBoard(int delay) {
        UpdatingBoard updatingBoard = new CraftUpdatingBoard(this.plugin, delay);

        this.boards.add(updatingBoard);
        this.updatingBoards.add(updatingBoard);

        return updatingBoard;
    }

    @Override
    public void untrackBoard(Board board) {
        this.boards.remove(board);
        this.updatingBoards.remove(board);
    }

    @Override
    public void clear() {
        this.boards.forEach(Board::clear);
        this.boards.clear();
        this.updatingBoards.clear();
    }
}
