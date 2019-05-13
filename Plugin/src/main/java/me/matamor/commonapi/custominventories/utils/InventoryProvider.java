package me.matamor.commonapi.custominventories.utils;

import me.matamor.commonapi.custominventories.inventories.CustomInventory;

public interface InventoryProvider<T extends CustomInventory> {

    T newInstance();

}
