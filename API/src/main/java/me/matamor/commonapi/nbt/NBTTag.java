package me.matamor.commonapi.nbt;

public interface NBTTag<T> {

    T getValue();

    void setValue(T value);

}
