package me.matamor.commonapi;

import me.matamor.commonapi.top.Top;
import me.matamor.commonapi.top.TopEntry;
import me.matamor.commonapi.top.TopValue;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class SimpleTop implements Top {

    private int limit;
    private long delay;

    private final Map<Integer, TopValue> values;
    private final Map<Integer, TopEntry> entries;

    public SimpleTop() {
        this(1000, TimeUnit.MINUTES.toMillis(10));
    }

    public SimpleTop(int limit, long delay) {
        this.limit = limit;
        this.delay = delay;

        this.values = new LinkedHashMap<>();
        this.entries = new LinkedHashMap<>();
    }

    @Override
    public int getLimit() {
        return this.limit;
    }

    @Override
    public boolean hasLimit() {
        return this.limit > 0;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public long getDelay() {
        return this.delay;
    }

    @Override
    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public Map<Integer, TopValue> getValues() {
        return Collections.unmodifiableMap(this.values);
    }

    @Override
    public Map<Integer, TopEntry> getEntries() {
        return Collections.unmodifiableMap(this.entries);
    }
}
