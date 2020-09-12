package me.matamor.commonapi.utils;

import java.util.concurrent.TimeUnit;

public class Interval {

    private final long time;

    private long lastTime;

    public Interval(TimeUnit timeUnit, long time) {
        this(timeUnit, time, 0);
    }

    public Interval(TimeUnit timeUnit, long time, long delay) {
        this.time = TimeUnit.MILLISECONDS.convert(time, timeUnit);
        this.lastTime = System.currentTimeMillis() + delay;
    }

    public boolean check() {
        long now = System.currentTimeMillis();
        if (now >= this.time + this.lastTime) {
            this.lastTime = now;
            return true;
        }

        return false;
    }
}
