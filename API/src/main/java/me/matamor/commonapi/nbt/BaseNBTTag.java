package me.matamor.commonapi.nbt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class BaseNBTTag<T> implements NBTTag<T> {

    @Getter @Setter
    private T value;

}
