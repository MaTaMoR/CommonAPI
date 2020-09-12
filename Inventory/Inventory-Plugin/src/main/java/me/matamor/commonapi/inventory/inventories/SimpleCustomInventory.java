package me.matamor.commonapi.inventory.inventories;

import lombok.Getter;
import lombok.Setter;
import me.matamor.commonapi.inventory.InventoryModule;
import me.matamor.commonapi.inventory.icons.InventoryIcon;
import me.matamor.commonapi.inventory.item.CustomItem;
import me.matamor.commonapi.utils.Validate;
import me.matamor.commonapi.utils.inventory.InventorySize;
import me.matamor.commonapi.utils.replacement.PlayerVariables;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Level;

public class SimpleCustomInventory implements CustomInventory {

    private final Map<Integer, InventoryIcon> icons = new LinkedHashMap<>();
    private final Set<Player> viewers = new HashSet<>();

    @Getter
    private InventorySize size;

    @Getter
    private String title;

    @Setter
    private boolean interactInventory = true;

    public SimpleCustomInventory() {
        this(InventorySize.ONE_LINE, "");
    }

    public SimpleCustomInventory(InventorySize size, String title) {
        this(size, title, null);
    }

    public SimpleCustomInventory(InventorySize size, String title, Map<Integer, InventoryIcon> defaults) {
        Validate.notNull(size, "Size can't be null!");
        Validate.notNull(title, "Title can't be null!");

        this.size = size;
        this.title = title;

        if (defaults != null) {
            defaults.forEach(this::setIcon);
        }
    }

    @Override
    public void setSize(@NotNull InventorySize size) {
        Validate.notNull(size, "Size can't be null!");

        this.size = size;
    }

    @Override
    public void setTitle(@NotNull String title) {
        Validate.notNull(title, "Title can't be null!");

        this.title = title;
    }

    @Override
    public boolean canInteractInventory() {
        return this.interactInventory;
    }

    @Override
    public void setIcon(int position, @NotNull InventoryIcon icon) {
        this.icons.put(position, icon);
    }

    @Override
    public InventoryIcon getIcon(int position) {
        return this.icons.get(position);
    }

    @NotNull
    @Override
    public Map<Integer, InventoryIcon> getIcons() {
        return Collections.unmodifiableMap(this.icons);
    }

    @Override
    public boolean hasInventory(@NotNull Player player) {
        //Checks if the TopInventory has the same Holder as our CustomInventoryHolder, if so gets the CustomInventory of the Holder and checks if it's this Inventory
        return (player.getOpenInventory().getTopInventory().getHolder() instanceof CustomInventoryHolder) &&
                (((CustomInventoryHolder) player.getOpenInventory().getTopInventory().getHolder()).getCustomInventory() == this);
    }

    @NotNull
    @Override
    public Inventory createInventory(@NotNull Player player) {
        //Creates an empty Inventory to be later filled
        Inventory inventory = Bukkit.createInventory(new CustomInventoryHolder(this), this.size.getSize(), PlayerVariables.replace(this.title, player));

        //Loop trough all the Icons set
        for (Map.Entry<Integer, InventoryIcon> entry : this.icons.entrySet()) {
            //If the position is off limits just ignore the Icon
            if (entry.getKey() < 0 || entry.getKey() >= this.size.getSize()) {
                continue;
            }

            try {
                CustomItem customItem = entry.getValue().getItem(player);
                if (customItem != null) {
                    inventory.setItem(entry.getKey(), customItem.build(player));
                }
            } catch (Exception e) {
                InventoryModule.getInstance().getLogger().log(Level.SEVERE, "Error while building item in slot: " + entry.getKey(), e);
            }
        }

        return inventory;
    }


    @NotNull
    @Override
    public Inventory openInventory(@NotNull Player player) {
        Inventory inventory = createInventory(player);

        player.openInventory(inventory);

        return inventory;
    }

    @Override
    public void update() {
        Iterator<Player> iterator = this.viewers.iterator();

        while (iterator.hasNext()) {
            Player player = iterator.next();

            if (player.isOnline()) {
                update(player);
            } else {
                iterator.remove();
            }
        }
    }

    @NotNull
    @Override
    public Collection<Player> getViewers() {
        return this.viewers;
    }

    @Override
    public boolean update(@NotNull Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if (inventory.getHolder() instanceof CustomInventoryHolder) {
            CustomInventoryHolder customHolder = (CustomInventoryHolder) inventory.getHolder();

            //Set instance of current CustomInventory
            if (customHolder.getCustomInventory() != this) {
                customHolder.setCustomInventory(this);
            }

            //Not the same size so open another inventory
            if (getSize().getSize() != inventory.getSize() || !Objects.equals(player.getOpenInventory().getTitle(), PlayerVariables.replace(this.title, player))) {
                openInventory(player);
            } else {
                Inventory newInventory = createInventory(player);

                ItemStack[] currentContents = inventory.getContents();
                ItemStack[] newContents = newInventory.getContents();

                for (int i = 0; currentContents.length > i; i++) {
                    ItemStack oldStack = currentContents[i];
                    ItemStack newStack = newContents[i];

                    if (oldStack == null && newStack != null) {
                        currentContents[i] = newStack;
                    } else if (oldStack != null && newStack != null) {
                        if (!oldStack.isSimilar(newStack)) {
                            currentContents[i] = newStack;
                        }
                    } else {
                        currentContents[i] = null;
                    }
                }

                inventory.setContents(currentContents);
            }

            return true;
        }

        return false;
    }
}
