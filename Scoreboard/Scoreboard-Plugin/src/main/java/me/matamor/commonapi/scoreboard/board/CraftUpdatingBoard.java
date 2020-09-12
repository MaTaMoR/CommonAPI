package me.matamor.commonapi.scoreboard.board;

import lombok.Getter;
import me.matamor.commonapi.scoreboard.ScoreboardModule;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class CraftUpdatingBoard extends CraftBoard implements UpdatingBoard {

    @Getter
    private final long delay;

    @Getter
    private long lastUpdate;

    private boolean lastError;

    public CraftUpdatingBoard(@NotNull ScoreboardModule plugin, long delay) {
        super(plugin);

        this.delay = delay;
    }

    public boolean update(long now) {
        boolean shouldUpdate = (now - this.lastUpdate) >= this.delay;

        if (shouldUpdate) {
            try {
                update();

                this.lastUpdate = now;
                this.lastError = false;
            } catch (Exception e) {
                getPlugin().getLogger().log(Level.INFO, "There was an error while updating the Scoreboard: " + getId(), e);

                //This system will accept one error, if the last update was an error and this one is an error aswell the board won't be update
                if (this.lastError) {
                    getPlugin().getLogger().log(Level.INFO, "This was the second error while updating the Scoreboard '" + getId() + "'... It won't be upated any longer.");
                    return false;
                }

                this.lastError = true;
            }
        }

        return true;
    }
}
