package me.matamor.commonapi.inventory.inventories;

import me.matamor.commonapi.inventory.icons.InventoryIcon;
import me.matamor.commonapi.utils.inventory.InventorySize;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

public interface CustomInventory {

    /**
     * Gets the size
     * @return the size of the Inventory
     */

    @NotNull
    InventorySize getSize();

    /**
     * Sets the Size of the Inventory
     * @param inventorySize the target Size to be set, can't be null!
     */

    void setSize(@NotNull InventorySize inventorySize);

    /**
     * Gets the title
     * @return the title of the Inventory
     */

    @NotNull
    String getTitle();

    /**
     * Sets the Title of the Inventory
     * @param title the target Title to be set, can't be null!
     */

    void setTitle(@NotNull String title);

    /**
     * Checks if Inventory is interactable
     * @return true if interactable
     */

    boolean canInteractInventory();

    /**
     * Sets if the Inventory is interactable
     * @param interactInventory the target value
     */

    void setInteractInventory(boolean interactInventory);

    /**
     * Sets and Icon on given Position
     * @param position the position where the Icon will be placed
     * @param icon the Icon that will be placed at the given position, can't be null!
     */

    void setIcon(int position, @NotNull InventoryIcon icon);

    /**
     * Returns an Icon
     * @param position the position where the Icon should be
     * @return the Icon at the given position, can be null!
     */

    @Nullable
    InventoryIcon getIcon(int position);

    /**
     * Returns the Icons
     * @return a Map of the Icons, can't be null!
     */

    @NotNull
    Map<Integer, InventoryIcon> getIcons();

    /**
     * Checks if given player has this Inventory open
     * @param player the target player
     * @return true if the player has the Inventory open
     */

    boolean hasInventory(@NotNull Player player);

    /**
     * Create an Inventory for the given player
     * @param player the target player, can't be null!
     * @return the Inventory for the given player
     */

    @NotNull
    Inventory createInventory(@NotNull Player player);

    /**
     * Creates an Inventory for the given player and opens it, works just like this method
     * @see CustomInventory#createInventory(Player)
     * @param player the target player, can't be null!
     * @return the Inventory for the given player
     */

    @NotNull
    Inventory openInventory(@NotNull Player player);

    /**
     * Updates the Inventory of the given player if the player has currently open this Inventory
     * @param player the target player, can't be null!
     * @return true if the inventory was updated!
     */

    boolean update(@NotNull Player player);

    /**
     * Updates the Inventory for all the players viewing it!
     */

    void update();

    /**
     * Returns the viewers
     * @return a list of the Players with this inventory currently open
     */

    @NotNull
    Collection<Player> getViewers();

    /**
     * A method called when the Inventory is open by a Player, this is called on an Event level
     * @param player the player opening the Inventory, can't be null!
     */

    default void onOpen(@NotNull Player player) {

    }

    /**
     * A method called when the Inventory is closed by a Player, this is called on an Event level
     * @param player the player opening the Inventory, can't be null!
     */

    default void onClose(Player player) {

    }
}
