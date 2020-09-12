package me.matamor.commonapi.container;

public interface ContainerEntry<T> {

    Container<T> getContainer();

    String getPath();

    T getDefault();

    default T get() {
        return getContainer().get(this);
    }
}
