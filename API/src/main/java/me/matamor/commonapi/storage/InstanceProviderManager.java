package me.matamor.commonapi.storage;

import java.util.Map.Entry;
import java.util.Set;

public interface InstanceProviderManager {

    <T> void registerProvider(Class<T> clazz, InstanceProvider<T> provider);

    <T> void registerProvider(Class<T> clazz, InstanceProvider<T> provider, boolean register);

    void unregisterProvider(Class<?> clazz);

    void unregisterProvider(Class<?> clazz, boolean unregister);

    Set<Entry<Class<?>, InstanceProvider<?>>> getEntries();
}
