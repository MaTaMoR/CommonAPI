package me.matamor.commonapi.inventory.icons;

import me.matamor.commonapi.inventory.actions.ActionHandler;
import me.matamor.commonapi.inventory.enums.ClickType;
import me.matamor.commonapi.inventory.item.CustomItem;
import me.matamor.commonapi.requirements.Requirement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface InventoryIcon {

    /**
     * Gets the ClickType of the Icon
     * @return the ClickType, can't be null!
     */

    @NotNull
    ClickType getClickType();

    /**
     * Sets a Requirement
     * @param requirement the target Requirement to be set, can be null!
     */

    void setRequirement(@Nullable Requirement requirement);

    /**
     * Checks if a Requirement is set
     * @return true if a Requirement is set
     */

    boolean hasRequirement();

    /**
     * Gets the Requirement
     * @return the Requirement of the Icon, can be null!
     */

    @Nullable
    Requirement getRequirement();

    /**
     * Sets if the Visibility of the Icon is restricted
     * if set to 'true' and the Icon has a Requirement set
     * only the players that have the Requirement will se this Icon
     * @param visibilityRestricted the target visibility to be set!
     */

    void setVisibilityRestricted(boolean visibilityRestricted);

    /**
     * Returns the Visibility
     * @return true if the Icon has restricted visibility
     */

    boolean hasVisibilityRestricted();

    /**
     * Gets the CustomItem of the Inventory for the given Player
     * @param player the target player, can be null!
     * @return the CustomItem of the given player, may return null if the Player is null and  the Icon doesn't support a null Player
     */

    @Nullable
    CustomItem getItem(@Nullable Player player);

    /**
     * Adds the ActionHandlers to the Icon
     * @param actionHandlers the target ActionHandlers to be added, can't be null!
     * @return returns the self Icon
     */

    @NotNull
    InventoryIcon addClickAction(@NotNull ActionHandler... actionHandlers);

    /**
     * Removes the ActionHandlers from the Icon
     * @param actionHandlers the target ActionHandlers to be removed, can't be null!
     */

    void removeClickAction(@NotNull ActionHandler... actionHandlers);

    /**
     * Gets the ActionHandlers
     * @return the ActionHandlers from the Icon
     */

    Collection<ActionHandler> getClickActions();

}
