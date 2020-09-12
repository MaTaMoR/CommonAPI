package me.matamor.commonapi.inventory.actions.defaults;

import lombok.Getter;
import me.matamor.commonapi.inventory.actions.ClickAction;
import me.matamor.commonapi.utils.Validate;
import me.matamor.commonapi.utils.replacement.PlayerVariables;
import org.bukkit.entity.Player;

public class SendMessage implements ClickAction {

    @Getter
    private final String message;

    public SendMessage(String message) {
        Validate.notNull(message, "Message can't be null!");

        this.message = message;
    }

    @Override
    public void execute(Player player) {
        player.sendMessage(PlayerVariables.replace(this.message, player));
    }
}
