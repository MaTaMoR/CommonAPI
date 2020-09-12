package me.matamor.commonapi.inventory.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.matamor.commonapi.inventory.amount.Amount;
import org.bukkit.Material;

@RequiredArgsConstructor
public class MaterialEntry {

    @Getter
    private final Material material;

    @Getter
    private final short dataValue;

    @Getter
    private final Amount amount;

}
