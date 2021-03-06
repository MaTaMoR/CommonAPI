package me.matamor.commonapi.inventory.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.inventory.icons.InventoryIcon;

import java.util.Arrays;

@AllArgsConstructor
public class InventoryLine {

    private final InventoryIcon[] inventoryIcons = new InventoryIcon[9];

    @Getter
    private final int line;

    public void setIcon(int position, InventoryIcon icon) {
        if (position < 0 || position >= getSize()) {
            throw new IndexOutOfBoundsException("Index: " + position + ", Size: " + getSize());
        }

        this.inventoryIcons[position] = icon;
    }

    public InventoryIcon getIcon(int position) {
        if (position < 0 || position >= getSize()) {
            throw new IndexOutOfBoundsException("Index: " + position + ", Size: " + getSize());
        }

        return this.inventoryIcons[position];
    }

    public int getSize() {
        return this.inventoryIcons.length;
    }

    public InventoryIcon[] getInventoryIcons() {
        return Arrays.copyOf(this.inventoryIcons, getSize());
    }
}
