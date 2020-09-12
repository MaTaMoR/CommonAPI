package me.matamor.commonapi.inventory.inventories;

import lombok.Getter;
import me.matamor.commonapi.inventory.InventoryModule;
import me.matamor.commonapi.utils.BukkitTaskHandler;
import me.matamor.commonapi.utils.inventory.InventorySize;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class SimpleUpdatingCustomInventory extends SimpleCustomInventory implements CustomUpdatingInventory {

    @Getter
    private final long ticks;

    @Getter
    private final BukkitTaskHandler taskHandler;

    public SimpleUpdatingCustomInventory(TimeUnit timeUnit, long delay) {
        this(InventorySize.ONE_LINE, "", timeUnit, delay);
    }

    public SimpleUpdatingCustomInventory(InventorySize size, String title, TimeUnit timeUnit, long delay) {
        super(size, title);

        this.ticks = timeUnit.convert(delay, TimeUnit.MILLISECONDS) / 50;
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
    }

    @Override
    public final void onOpen(@NotNull Player player) {
        this.taskHandler.start();
    }

    @Override
    public final void onClose(Player player) {
        if (this.taskHandler.isRunning() && getViewers().isEmpty()) {
            this.taskHandler.stop();
        }
    }
}
