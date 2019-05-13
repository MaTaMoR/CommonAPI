package me.matamor.commonapi.custominventories.utils.replacement;

import org.bukkit.entity.Player;

public interface VariableReplacer {

    String replace(String text);

    String replace(String text, Player player);

}
