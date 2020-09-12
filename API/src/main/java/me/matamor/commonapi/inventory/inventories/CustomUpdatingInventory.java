package me.matamor.commonapi.inventory.inventories;

import me.matamor.commonapi.utils.BukkitTaskHandler;

public interface CustomUpdatingInventory {

    BukkitTaskHandler getTaskHandler();

    long getTicks();

    default boolean shouldUpdate() {
        return true;
    }

    default void onUpdate() {

    }

}
