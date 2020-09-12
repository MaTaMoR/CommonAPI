package me.matamor.commonapi.inventory.inventories;

import lombok.Getter;
import me.matamor.commonapi.inventory.InventoryModule;
import me.matamor.commonapi.utils.BukkitTaskHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class PersonalUpdatingCustomInventory implements CustomUpdatingInventory {

    @Getter
    private final Set<Player> viewers = new HashSet<>();

    @Getter
    private final long ticks;

    @Getter
    private final BukkitTaskHandler taskHandler;

    @Getter
    private final Consumer<CustomInventory> inventoryFiller;

    public PersonalUpdatingCustomInventory(TimeUnit timeUnit, long delay, Consumer<CustomInventory> inventoryFiller) {
        this.ticks = TimeUnit.MILLISECONDS.convert(delay, timeUnit) / 50;

        this.taskHandler = new BukkitTaskHandler() {
            @Override
            public BukkitTask createTask() {
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        update();
                    }
                }.runTaskTimer(InventoryModule.getInstance(), getTicks(), getTicks());
            }
        };

        this.inventoryFiller = inventoryFiller;
    }

    public CustomInventory createCustomInventory(Player player) {
        CustomInventory customInventory = new SimpleCustomInventory() {

            @Override
            public void onOpen(Player target) {
                PersonalUpdatingCustomInventory.this.getViewers().add(target);

                getTaskHandler().start();
            }

            @Override
            public void onClose(Player target) {
                PersonalUpdatingCustomInventory.this.getViewers().remove(target);

                if (getTaskHandler().isRunning() && PersonalUpdatingCustomInventory.this.getViewers().isEmpty()) {
                    getTaskHandler().stop();
                }
            }
        };

        this.inventoryFiller.accept(customInventory);

        return customInventory;
    }

    public Inventory createInventory(Player player) {
        return createCustomInventory(player).createInventory(player);
    }

    public Inventory openInventory(Player player) {
        Inventory inventory = createInventory(player);

        player.openInventory(inventory);

        return inventory;
    }

    public void update() {
        Iterator<Player> iterator = this.viewers.iterator();

        while (iterator.hasNext()) {
            Player player = iterator.next();

            if (player.isOnline()) {
                createCustomInventory(player).update(player);
            } else {
                iterator.remove();
            }
        }
    }
}
