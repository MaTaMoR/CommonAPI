package me.matamor.commonapi.inventory.utils;

import lombok.Getter;
import me.matamor.commonapi.inventory.icons.InventoryIcon;
import me.matamor.commonapi.inventory.inventories.CustomInventory;
import me.matamor.commonapi.utils.inventory.InventorySize;

import java.util.Arrays;

public class InventoryTemplate {

    @Getter
    private final String title;

    @Getter
    private final InventorySize size;

    private final InventoryLine[] inventoryLines;

    public InventoryTemplate(InventorySize size, String title) {
        this.title = title;
        this.size = size;

        this.inventoryLines = new InventoryLine[size.getPosition()];

        for (int i = 0; size.getPosition() > i; i++) {
            this.inventoryLines[i] = new InventoryLine(i);
        }
    }

    public InventoryLine getLine(int position) {
        if (position < 0 || position >= getSize().getPosition()) {
            throw new IndexOutOfBoundsException("Index: " + position + ", Size: " + getSize().getPosition());
        }

        return this.inventoryLines[position];
    }

    public InventoryLine getFirstLine() {
        return this.inventoryLines[0];
    }

    public InventoryLine getLastLine() {
        return this.inventoryLines[this.inventoryLines.length - 1];
    }

    public InventoryLine[] getInventoryLines() {
        return Arrays.copyOf(this.inventoryLines, this.inventoryLines.length);
    }

    public <T extends CustomInventory> T createInventory(T customInventory) {
        customInventory.setSize(this.size);
        customInventory.setTitle(this.title);

        fillInventory(customInventory);

        return customInventory;
    }

    private void fillInventory(CustomInventory customInventory) {
        for (int line = 0; this.inventoryLines.length > line; line++) {
            InventoryLine inventoryLine = this.inventoryLines[line];
            InventoryIcon[] inventoryIcons = inventoryLine.getInventoryIcons();

            for (int position = 0; inventoryIcons.length > position; position++) {
                InventoryIcon inventoryIcon = inventoryIcons[position];
                if (inventoryIcon == null) continue;

                customInventory.setIcon((9 * line) + position, inventoryIcon);
            }
        }
    }
}
