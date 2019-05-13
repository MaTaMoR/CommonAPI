package me.matamor.commonapi.custominventories.inventories;

import me.matamor.commonapi.custominventories.utils.BasicTaskHandler;

public interface CustomUpdatingInventory {

    BasicTaskHandler getTaskHandler();

    long getTicks();

    default boolean shouldUpdate() {
        return true;
    }

    default void onUpdate() {

    }

}
