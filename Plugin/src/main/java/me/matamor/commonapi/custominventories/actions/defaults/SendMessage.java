package me.matamor.commonapi.custominventories.actions.defaults;

import lombok.Getter;
import me.matamor.commonapi.custominventories.actions.ClickAction;
import me.matamor.commonapi.custominventories.utils.InventoryUtils;
import me.matamor.commonapi.utils.Validate;
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
        player.sendMessage(InventoryUtils.fullFormat(this.message, player));
    }
}
