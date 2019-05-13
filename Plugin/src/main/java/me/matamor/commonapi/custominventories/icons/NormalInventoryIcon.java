package me.matamor.commonapi.custominventories.icons;

import me.matamor.commonapi.custominventories.enums.ClickType;
import me.matamor.commonapi.custominventories.utils.CustomItem;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.entity.Player;

public class NormalInventoryIcon extends SimpleInventoryIcon {

    private final CustomItem item;

    public NormalInventoryIcon(CustomItem item) {
        this(ClickType.BOTH_CLICKS, item);
    }

    public NormalInventoryIcon(ClickType clickType, CustomItem item) {
        super(clickType);

        Validate.notNull(item, "Item can't be null!");

        this.item = item;
    }

    @Override
    public CustomItem getItem(Player player) {
        return this.item;
    }
}
