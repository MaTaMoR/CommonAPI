package me.matamor.commonapi.scoreboard;

import lombok.Getter;
import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import me.matamor.commonapi.commands.ICommandException;
import me.matamor.commonapi.modules.java.JavaModule;
import me.matamor.commonapi.scoreboard.board.Board;
import me.matamor.commonapi.scoreboard.board.CraftScoreboardManager;
import me.matamor.commonapi.scoreboard.board.lines.AnimationBoardLine;
import me.matamor.commonapi.scoreboard.board.lines.ScrollBoardLine;
import me.matamor.commonapi.scoreboard.board.lines.TextBoardLine;
import me.matamor.commonapi.utils.NMSClassLoader;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ScoreboardModule extends JavaModule {

    @Getter
    private static ScoreboardModule instance;

    @Getter
    private ScoreboardController scoreboardController;

    @Getter
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        if (!setupController()) {
            throw new RuntimeException("Couldn't find a valid Scoreboard controller!");
        }

        this.scoreboardManager = new CraftScoreboardManager(this);

        Board board = this.scoreboardManager.createUpdatingBoard(1);
        board.setDisplayName(new TextBoardLine("&8Test"));
        board.setLine(15, new AnimationBoardLine(Arrays.asList("&7Hola", "&bHola"), 20));
        board.setLine(14, new ScrollBoardLine("La bebecita bebe lean y bebe whisky", 10, 4, 5));

        ICommand<ScoreboardModule> command = new ICommand<ScoreboardModule>(this, "boardtest", new String[] { }) {
            @Override
            public void onCommand(CommandArgs commandArgs) throws ICommandException {
                Player player = commandArgs.getPlayer();
                board.show(player);
            }
        };

        command.register();
    }

    @Override
    public void onDisable() {
        instance = null;

        if (this.scoreboardManager != null) {
            this.scoreboardManager.clear();
        }
    }

    private boolean setupController() {
        NMSClassLoader<ScoreboardController> hologramController = new NMSClassLoader<>(this, "me.matamor.commonapi.nms.{version}.scoreboard.ScoreboardControllerImpl");
        if (hologramController.load()) {
            this.scoreboardController = hologramController.getValue();
        }

        return this.scoreboardController != null;
    }
}
