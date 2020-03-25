package me.matamor.commonapi.container;

import me.matamor.commonapi.config.IConfig;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public interface Container<T> {

    Plugin getPlugin();

    IConfig getConfig();

    Serializer<T> getSerializer();

    Collection<ContainerEntry<T>> getEntries();

    boolean hasEntry(ContainerEntry<T> entry);

    T get(ContainerEntry<T> entry);

    void load();
}
