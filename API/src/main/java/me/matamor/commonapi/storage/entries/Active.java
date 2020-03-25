package me.matamor.commonapi.storage.entries;

public interface Active<T> {

    void setActive(T active);

    boolean hasActive();

    T getActive();

}
