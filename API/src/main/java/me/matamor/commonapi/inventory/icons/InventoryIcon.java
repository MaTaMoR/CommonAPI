package me.matamor.commonapi.inventory.icons;

import me.matamor.commonapi.inventory.actions.ActionHandler;
import me.matamor.commonapi.inventory.enums.ClickType;
import me.matamor.commonapi.inventory.item.CustomItem;
import me.matamor.commonapi.requirements.Requirement;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface InventoryIcon {

    ClickType getClickType();

    void setRequirement(Requirement requirement);

    boolean hasRequirement();

    Requirement getRequirement();

    String getRequirementMessage();

    void setRequirementMessage(String requirementMessage);

    void setVisibilityRestricted(boolean visibilityRestricted);

    boolean hasVisibilityRestricted();

    CustomItem getItem(Player player);

    InventoryIcon addClickAction(ActionHandler... actionHandlers);

    void removeClickAction(ActionHandler... actionHandlers);

    Collection<ActionHandler> getClickActions();

}
