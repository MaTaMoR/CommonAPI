package me.matamor.commonapi.nbt.defaults;

import me.matamor.commonapi.nbt.BaseNBTTag;
import me.matamor.commonapi.nbt.NBTTag;

import java.util.List;

public class NBTTagList extends BaseNBTTag<List<NBTTag<?>>> {

    public NBTTagList(List<NBTTag<?>> value) {
        super(value);
    }
}
