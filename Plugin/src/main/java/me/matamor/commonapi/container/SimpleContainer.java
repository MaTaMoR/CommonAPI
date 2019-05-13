package me.matamor.commonapi.container;

import lombok.Getter;
import me.matamor.commonapi.config.IConfig;
import me.matamor.commonapi.utils.serializer.SerializationException;
import me.matamor.commonapi.utils.serializer.Serializer;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.logging.Level;

public abstract class SimpleContainer<T> implements Container<T> {

    private final Map<ContainerEntry<T>, T> data = new LinkedHashMap<>();

    @Getter
    private final Plugin plugin;

    @Getter
    private final Serializer<T> serializer;

    private final List<ContainerEntry<T>> entries;

    public SimpleContainer(Plugin plugin, Serializer<T> serializer, Collection<ContainerEntry<T>> entries) {
        this.plugin = plugin;
        this.serializer = serializer;
        this.entries = new ArrayList<>(entries);
    }

    @Override
    public Collection<ContainerEntry<T>> getEntries() {
        return Collections.unmodifiableList(this.entries);
    }

    @Override
    public boolean hasEntry(ContainerEntry<T> entry) {
        return this.data.containsKey(entry);
    }

    @Override
    public T get(ContainerEntry<T> entry) {
        return (this.data.containsKey(entry) ? this.data.get(entry) : entry.getDefault());
    }

    @Override
    public void load() {
        this.data.clear();

        if (this.entries.size() > 0) {
            IConfig config = getConfig();

            for (ContainerEntry<T> entry : this.entries) {
                Object value = config.get(entry.getPath());
                T deserialized = entry.getDefault();

                try {
                    if (value == null) {
                        config.set(entry.getPath(), this.serializer.serialize(entry.getDefault()));
                    } else {
                        deserialized = this.serializer.deserialize(value);
                    }
                } catch (SerializationException e) {
                    this.plugin.getLogger().log(Level.SEVERE, String.format("[%s] Serialization error", getClass().getSimpleName()), e);
                }

                this.data.put(entry, deserialized);
            }

            config.save();
        }
    }
}
