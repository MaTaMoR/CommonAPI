package me.matamor.commonapi.inventory.utils;

import me.matamor.commonapi.inventory.inventories.CustomInventory;

public interface InventoryProvider<T extends CustomInventory> {

    T newInstance();

}
