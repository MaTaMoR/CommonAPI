package me.matamor.commonapi.custominventories.utils;

import me.matamor.commonapi.CommonAPI;
import me.matamor.commonapi.custominventories.reflections.PlayerReflections;
import me.matamor.commonapi.custominventories.utils.replacement.VariableReplacer;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public abstract class InventoryVariables {

    private static final Map<String, InventoryVariable> INVENTORY_VARIABLES = new LinkedHashMap<>();
    private static final Map<String, VariableReplacer> VARIABLE_REPLACER = new LinkedHashMap<>();

    public static final InventoryVariable NAME = register(new SimpleInventoryVariable("{name}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            return player.getName();
        }
    });

    public static final InventoryVariable ONLINE_PLAYERS = register(new SimpleInventoryVariable("{online_players}") {
        @Override
        public String getReplacement(Player player) {
            return String.valueOf(Players.getOnlinePlayers());
        }
    });

    public static final InventoryVariable MAX_HEALTH = register(new SimpleInventoryVariable("{max_health}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            return String.valueOf(Math.round(player.getMaxHealth()));
        }
    });

    public static final InventoryVariable HEALTH = register(new SimpleInventoryVariable("{health") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            return String.valueOf(Math.round(player.getHealth()));
        }
    });

    public static final InventoryVariable LEVEL = register(new SimpleInventoryVariable("{level}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            return String.valueOf(player.getLevel());
        }
    });

    public static final InventoryVariable LOCATION = register(new SimpleInventoryVariable("{location}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            Location location = player.getLocation();
            return "XYZ: " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
        }
    });

    public static final InventoryVariable PING = register(new SimpleInventoryVariable("{ping}") {
        @Override
        public String getReplacement(Player player) {
            if (player == null) return null;

            return String.valueOf(PlayerReflections.getPing(player));
        }
    });

    public static InventoryVariable register(InventoryVariable variable) {
        Validate.notNull(variable, "InventoryVariable can't be null!");
        Validate.notNull(variable.getVariable(), "InventoryVariable's variable can't be null!");

        INVENTORY_VARIABLES.put(variable.getVariable(), variable);

        return variable;
    }

    public static InventoryVariable getVariable(String name) {
        return INVENTORY_VARIABLES.get(name);
    }

    public static void unregister(InventoryVariable variable) {
        Validate.notNull(variable, "InventoryVariable can't be null");

        INVENTORY_VARIABLES.remove(variable.getVariable());
    }

    public static Collection<InventoryVariable> getVariables() {
        return Collections.unmodifiableCollection(INVENTORY_VARIABLES.values());
    }

    public static VariableReplacer registerReplacer(String name, VariableReplacer variableReplacer) {
        Validate.notNull(name, "Name can't be null!");
        Validate.notNull(variableReplacer, "VariableReplacer can't be null!");
        Validate.isFalse(VARIABLE_REPLACER.containsKey(name), "The VariableReplacer '" + name + "' is already registered!");

        VARIABLE_REPLACER.put(name, variableReplacer);

        return variableReplacer;
    }

    public static void unregisterReplacer(String name) {
        Validate.notNull(name, "Name can't be null!");

        VARIABLE_REPLACER.remove(name);
    }

    public static Collection<VariableReplacer> getReplacers() {
        return Collections.unmodifiableCollection(VARIABLE_REPLACER.values());
    }

    public static String replace(String text) {
        return replace(text, null);
    }

    public static String replace(String text, Player player) {
        Validate.notNull(text, "Text can't be null");

        for (Map.Entry<String, InventoryVariable> entry : INVENTORY_VARIABLES.entrySet()) {
            if (!text.contains(entry.getKey())) continue;

            try {
                String replacement = entry.getValue().getReplacement(player);
                if (replacement != null) {
                    text = text.replace(entry.getKey(), replacement);

                    if (text.isEmpty()) {
                        return text;
                    }
                }
            } catch (Exception e) {
                CommonAPI.getInstance().getLogger().log(Level.SEVERE, "Error while getting replacement for variable: " + entry.getKey(), e);
            }
        }

        for (VariableReplacer replacer : VARIABLE_REPLACER.values()) {
            text = replacer.replace(text, player);

            if (text == null || text.isEmpty()) {
                return "";
            }
        }

        return text;
    }
}
