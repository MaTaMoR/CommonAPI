package me.matamor.commonapi.utils;

import lombok.Getter;
import me.matamor.commonapi.utils.map.ExpirationListener;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class LimitedDurationMap<K, V> {

    private final Map<K, CachedValue<V>> map = new ConcurrentHashMap<>();
    private final List<ExpirationListener<V>> listeners = new ArrayList<>();

    @Getter
    private final Plugin plugin;

    @Getter
    private final long ticks;

    @Getter
    private final long duration;

    public LimitedDurationMap(final Plugin plugin, final long ticks) {
        this.plugin = plugin;
        this.ticks = ticks;
        this.duration = ticks * 50;

        plugin.getServer().getScheduler().runTaskTimer(plugin, this::cleanup, ticks, ticks);
    }

    public void put(K key, V value) {
        this.map.put(key, new CachedValue<>(value));
    }

    public V get(K key) {
        CachedValue<V> value = this.map.get(key);
        if (value == null) return null;

        if (value.expired() && !call(value.getValue())) {
            this.map.remove(key);

            return null;
        } else {
            return value.getValue();
        }
    }

    public boolean containsKey(K key) {
        CachedValue<V> value = this.map.get(key);
        if (value == null) return false;

        if (value.expired() && !call(value.getValue())) {
            this.map.remove(key);

            return false;
        }

        return true;
    }

    public V remove(K key) {
        CachedValue<V> cachedValue = this.map.remove(key);
        return (cachedValue == null ? null : cachedValue.getValue());
    }

    public void cleanup() {
        this.map.entrySet().removeIf(entry -> entry.getValue().expired() && !call(entry.getValue().getValue()));
    }

    public Collection<V> getValues() {
        Collection<V> values = new ArrayList<>();

        Iterator<Entry<K, CachedValue<V>>> iterator = this.map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<K, CachedValue<V>> entry = iterator.next();

            //If entry is expired and listener doesn't return true remove it!
            if (entry.getValue().expired() && !call(entry.getValue().getValue())) {
                iterator.remove();
            } else {
                values.add(entry.getValue().getValue());
            }
        }

        return values;
    }

    public void clear() {
        this.map.clear();
    }

    public void addExpirationListener(ExpirationListener<V> expirationListener) {
        synchronized (this.listeners) {
            this.listeners.add(expirationListener);
        }
    }

    public synchronized void removeExpirationListener(ExpirationListener<V> expirationListener) {
        synchronized (this.listeners) {
            this.listeners.remove(expirationListener);
        }
    }

    public synchronized Collection<ExpirationListener<V>> getExpirationListeners() {
        return this.listeners;
    }

    private boolean call(V value) {
        if (value == null) return false;

        for (ExpirationListener<V> listener : this.listeners) {
            if (listener.onExpiration(value)) return true;
        }

        return false;
    }

    private class CachedValue<T> {

        private final T value;
        private long lastAccess;

        public CachedValue(T value) {
            this.value = value;
            this.lastAccess = System.currentTimeMillis();
        }

        public T getValue() {
            this.lastAccess = System.currentTimeMillis();
            return this.value;
        }

        public long getLastAccess() {
            return this.lastAccess;
        }

        public boolean expired() {
            return (this.lastAccess + getDuration()) - System.currentTimeMillis() <= 0;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            } else if (object instanceof CachedValue){
                return Objects.equals(this.value, ((CachedValue) object).value); //IDK if this can cause errors
            }

            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.value);
        }
    }
}
