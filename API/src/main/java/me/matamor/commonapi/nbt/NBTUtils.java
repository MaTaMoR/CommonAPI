package me.matamor.commonapi.nbt;

import me.matamor.commonapi.nms.NMSController;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface NBTUtils extends NMSController {

    Map<String, NBTTag<?>> getContent(ItemStack itemStack);

    ItemStack setTag(ItemStack itemStack, String key, NBTTag<?> tag);

    boolean hasTag(ItemStack itemStack, String key);

    NBTTag<?> getTag(ItemStack itemStack, String key);

    ItemStack removeTag(ItemStack itemStack, String key);

    ItemStack setContent(ItemStack itemStack, Map<String, NBTTag<?>> content);

}
