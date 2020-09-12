package me.matamor.commonapi.nms.v1_13_R2.hologram.nbt;

import me.matamor.commonapi.nbt.NBTTag;
import me.matamor.commonapi.nbt.NBTUtils;
import me.matamor.commonapi.nms.NMSVersion;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NBTTagUtils implements NBTUtils {

    @Override
    public NMSVersion getVersion() {
        return NMSVersion.v1_13_R2;
    }

    @Override
    public Map<String, NBTTag<?>> getContent(ItemStack itemStack) {
        Map<String, NBTTag<?>> content = new LinkedHashMap<>();

        net.minecraft.server.v1_13_R2.ItemStack handle = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tagCompound = handle.getTag();
        if (tagCompound == null) {
            return content;
        }

        for (String tagKey : tagCompound.getKeys()) {
            NBTBase nbtBase = tagCompound.get(tagKey);
            NBTTag<?> nbtTag = fromNMS(nbtBase);

            content.put(tagKey, nbtTag);
        }

        return content;
    }

    @Override
    public ItemStack setTag(ItemStack itemStack, String key, NBTTag<?> tag) {
        net.minecraft.server.v1_13_R2.ItemStack handle = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tagCompound = handle.getOrCreateTag();

        tagCompound.set(key, toNMS(tag));

        handle.setTag(tagCompound);

        return CraftItemStack.asCraftMirror(handle);
    }

    @Override
    public boolean hasTag(ItemStack itemStack, String key) {
        net.minecraft.server.v1_13_R2.ItemStack handle = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tagCompound = handle.getTag();
        if (tagCompound == null) {
            return false;
        }

        return tagCompound.hasKey(key);
    }

    @Override
    public NBTTag<?> getTag(ItemStack itemStack, String key) {
        net.minecraft.server.v1_13_R2.ItemStack handle = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tagCompound = handle.getTag();
        if (tagCompound == null) {
            return null;
        }

        NBTBase nbtBase = tagCompound.get(key);
        if (nbtBase == null) return null;

        return fromNMS(nbtBase);
    }

    @Override
    public ItemStack removeTag(ItemStack itemStack, String key) {
        net.minecraft.server.v1_13_R2.ItemStack handle = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tagCompound = handle.getTag();
        if (tagCompound == null) {
            return null;
        }

        tagCompound.remove(key);

        handle.setTag(tagCompound);

        return CraftItemStack.asCraftMirror(handle);
    }

    @Override
    public ItemStack setContent(ItemStack itemStack, Map<String, NBTTag<?>> content) {
        net.minecraft.server.v1_13_R2.ItemStack handle = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tagCompound = handle.getOrCreateTag();

        for (Map.Entry<String, NBTTag<?>> entry : content.entrySet()) {
            NBTBase nbtBase = toNMS(entry.getValue());

            tagCompound.set(entry.getKey(), nbtBase);
        }

        handle.setTag(tagCompound);

        return CraftItemStack.asCraftMirror(handle);
    }

    private NBTTag<?> fromNMS(NBTBase nbtBase) {
        if (nbtBase instanceof NBTTagString) { //NNS String Tag to API String Tag
            return new me.matamor.commonapi.nbt.defaults.NBTTagString(nbtBase.asString());
        } else if (nbtBase instanceof NBTTagInt) { //NMS Int Tag to API Int Tag
            return new me.matamor.commonapi.nbt.defaults.NBTTagInt(((NBTTagInt) nbtBase).asInt());
        } else if (nbtBase instanceof NBTTagDouble) { //NMS Double Tag to API Double Tag
            return new me.matamor.commonapi.nbt.defaults.NBTTagDouble(((NBTTagDouble) nbtBase).asDouble());
        } else if (nbtBase instanceof NBTTagByte) { // NMS Byte Tag to API Byte Tag
            return new me.matamor.commonapi.nbt.defaults.NBTTagByte(((NBTTagByte) nbtBase).asByte());
        } else if (nbtBase instanceof NBTTagShort) { // NMS Short Tag to API Short Tag
            return new me.matamor.commonapi.nbt.defaults.NBTTagShort(((NBTTagShort) nbtBase).asShort());
        } else if (nbtBase instanceof NBTTagLong) { // NMS Long Tag to API Long Tag
            return new me.matamor.commonapi.nbt.defaults.NBTTagLong(((NBTTagLong) nbtBase).asLong());
        } else if (nbtBase instanceof NBTTagList) { // NMS Tag List To API Tag List
            NBTTagList listTag = (NBTTagList) nbtBase;
            List<NBTTag<?>> list = new ArrayList<>();

            for (NBTBase aListTag : listTag) {
                list.add(fromNMS(aListTag));
            }

            return new me.matamor.commonapi.nbt.defaults.NBTTagList(list);
        } else if (nbtBase instanceof NBTTagIntArray) { // NMS Int Array to API Int Array TAG
            return new me.matamor.commonapi.nbt.defaults.NBTTagIntArray(((NBTTagIntArray) nbtBase).d());
        } else if (nbtBase instanceof NBTTagByteArray) { // NMS Byte Array to API Byte Array TAG
            return new me.matamor.commonapi.nbt.defaults.NBTTagByteArray(((NBTTagByteArray) nbtBase).c());
        } else { // Invalid NMS Tag
            throw new IllegalArgumentException("Invalid NBT: " + nbtBase.getClass());
        }
    }

    private NBTBase toNMS(NBTTag<?> nbtTag) {
        if (nbtTag instanceof me.matamor.commonapi.nbt.defaults.NBTTagString) { // API String TAG to NMS String Tag
            return new NBTTagString((String) nbtTag.getValue());
        } else if (nbtTag instanceof me.matamor.commonapi.nbt.defaults.NBTTagInt) { // API Int TAG to NMS Int Tag
            return new NBTTagInt((Integer) nbtTag.getValue());
        } else if (nbtTag instanceof me.matamor.commonapi.nbt.defaults.NBTTagDouble) { // API Double TAG to NMS Double Tag
            return new NBTTagDouble((Double) nbtTag.getValue());
        } else if (nbtTag instanceof me.matamor.commonapi.nbt.defaults.NBTTagByte) { // API Byte TAG to NMS Byte Tag
            return new NBTTagByte((Byte) nbtTag.getValue());
        } else if (nbtTag instanceof me.matamor.commonapi.nbt.defaults.NBTTagShort) { // API Short TAG to NMS Short Tag
            return new NBTTagShort((Short) nbtTag.getValue());
        } else if (nbtTag instanceof me.matamor.commonapi.nbt.defaults.NBTTagLong) { // API Long TAG to NMS Long Tag
            return new NBTTagLong((Long) nbtTag.getValue());
        } else if (nbtTag instanceof me.matamor.commonapi.nbt.defaults.NBTTagList) { // API Tag List TAG to NMS Tag List
            List<NBTTag<?>> apiTags = ((me.matamor.commonapi.nbt.defaults.NBTTagList) nbtTag).getValue();
            List<NBTBase> baseTags = new ArrayList<>();

            for (NBTTag<?> tag : apiTags) {
                baseTags.add(toNMS(tag));
            }

            NBTTagList tagList = new NBTTagList();
            tagList.addAll(baseTags);

            return tagList;
        } else if (nbtTag instanceof me.matamor.commonapi.nbt.defaults.NBTTagIntArray) { // API Int Array Tag to NMS Int Array Tag
            return new NBTTagIntArray((int[]) nbtTag.getValue());
        } else if (nbtTag instanceof me.matamor.commonapi.nbt.defaults.NBTTagByteArray) { // API Byte Array Tag NMS Byte Array Tag
            return new NBTTagByteArray((byte[]) nbtTag.getValue());
        } else { // Invalid API Tag
            throw new IllegalArgumentException("Invalid NBT: " + nbtTag.getClass());
        }
    }
}
