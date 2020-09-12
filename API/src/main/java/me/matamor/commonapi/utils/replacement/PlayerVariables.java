package me.matamor.commonapi.utils.replacement;

import me.matamor.commonapi.CommonAPIPlugin;
import me.matamor.commonapi.utils.Players;
import me.matamor.commonapi.utils.reflections.PlayerReflections;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PlayerVariables {

    private static final List<PlayerTextVariable> INVENTORY_VARIABLES = new ArrayList<>();

    public static final TextVariable<Player> NAME = register(new SimplePlayerTextVariable("{name}") {
        @Override
        public String getReplacement(Player player) {
            return (player == null ? null : player.getName());
        }
    });

    public static final TextVariable<Player> ONLINE_PLAYERS = register(new SimplePlayerTextVariable("{online_players}") {
        @Override
        public String getReplacement(Player player) {
            return String.valueOf(Players.getOnlinePlayers());
        }
    });

    public static final TextVariable<Player> MAX_HEALTH = register(new SimplePlayerTextVariable("{max_health}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            return String.valueOf(Math.round(player.getMaxHealth()));
        }
    });

    public static final TextVariable<Player> HEALTH = register(new SimplePlayerTextVariable("{health}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            return String.valueOf(Math.round(player.getHealth()));
        }
    });

    public static final TextVariable<Player> LEVEL = register(new SimplePlayerTextVariable("{level}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            return String.valueOf(player.getLevel());
        }
    });

    public static final TextVariable<Player> LOCATION = register(new SimplePlayerTextVariable("{location}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            Location location = player.getLocation();
            return "XYZ: " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
        }
    });

    public static final TextVariable<Player> PING = register(new SimplePlayerTextVariable("{ping}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            return String.valueOf(PlayerReflections.getPing(player));
        }
    });

    public static PlayerTextVariable register(@NotNull PlayerTextVariable variable) {
        INVENTORY_VARIABLES.add(variable);
        return variable;
    }

    public static void unregister(@NotNull PlayerTextVariable variable) {
        INVENTORY_VARIABLES.remove(variable);
    }

    public static List<PlayerTextVariable> getVariables() {
        return INVENTORY_VARIABLES;
    }

    public static String replace(@NotNull String text) {
        return replace(text, null);
    }

    public static String replace(@NotNull String text, @Nullable Player player) {
        for (PlayerTextVariable textVariable : INVENTORY_VARIABLES) {
            try {
                text = textVariable.replace(text, player);
            } catch (Exception e) {
                CommonAPIPlugin.getPlugin().getLogger().log(Level.SEVERE, "Error while replacing variable!", e);
            }
        }

        return text;
    }
}
