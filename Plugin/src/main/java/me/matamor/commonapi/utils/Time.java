package me.matamor.commonapi.utils;

import me.matamor.commonapi.messages.Messages;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
    This class is not mine!! I found it on Bukkit, i'm sorry i no longer know who made it! Though i have modified it a bit to fit my needs.

 */

public class Time {

    private static final long TICK_MS = 50;
    private static final long SECOND_MS = 1000;
    private static final long MINUTE_MS = SECOND_MS * 60;
    private static final long HOUR_MS = MINUTE_MS * 60;
    private static final long DAY_MS = HOUR_MS * 24;
    private static final long YEAR_MS = DAY_MS * 365;

    private static final Map<String, Long> unitMultipliers = new HashMap<>();

    private static void addTimeMultiplier(long multiplier, String... keys) {
        for (String key : keys) {
            unitMultipliers.put(key, multiplier);
        }
    }

    static {
        addTimeMultiplier(1, "ms", "milli", "millis", "millisecond", "milliseconds");
        addTimeMultiplier(TICK_MS, "t", "tick", "ticks");
        addTimeMultiplier(SECOND_MS, "s", "sec", "secs", "second", "seconds", "segundos", "segundo");
        addTimeMultiplier(MINUTE_MS, "m", "min", "mins", "minute", "minutes", "minutos", "minuto");
        addTimeMultiplier(HOUR_MS, "h", "hour", "hours", "horas", "hora");
        addTimeMultiplier(DAY_MS, "d", "day", "days", "dias", "dia");
        addTimeMultiplier(YEAR_MS, "y", "year", "years", "años", "año");
    }

    private long milliseconds;

    public Time(long time, TimeUnit timeUnit) {
        this(TimeUnit.MILLISECONDS.convert(time, timeUnit));
    }

    private Time(long milliseconds) {
        if (milliseconds < 0) {
            throw new IllegalArgumentException("Number of milliseconds cannot be less than 0");
        }

        this.milliseconds = milliseconds;
    }

    public long toMilliseconds() {
        return this.milliseconds;
    }

    public double toTicks() {
        return this.milliseconds / (double) TICK_MS;
    }

    public double toSeconds() {
        return this.milliseconds / (double) SECOND_MS;
    }

    public double toMinutes() {
        return this.milliseconds / (double) MINUTE_MS;
    }

    public double toHours() {
        return this.milliseconds / (double) HOUR_MS;
    }

    public double toDays() {
        return this.milliseconds / (double) DAY_MS;
    }

    public double toYears() {
        return this.milliseconds / (double) YEAR_MS;
    }

    @Override
    public String toString() {
        return toString(this.milliseconds);
    }

    public static Time parseString(String timeString) throws TimeParseException {
        if (timeString == null) {
            throw new IllegalArgumentException("timeString cannot be null");
        }

        if (timeString.isEmpty()) {
            throw new TimeParseException("Empty time string");
        }

        long totalMilliseconds = 0;

        boolean readingNumber = true;

        StringBuilder number = new StringBuilder();
        StringBuilder unit = new StringBuilder();

        for (char c : timeString.toCharArray()) {
            if (c == ' ' || c == ',') {
                readingNumber = false;
                continue;
            }

            if (c == '.' || (c >='0' && c <= '9')) {
                if (!readingNumber) {
                    totalMilliseconds += parseTimeComponent(number.toString(), unit.toString());

                    number.setLength(0);
                    unit.setLength(0);

                    readingNumber = true;
                }

                number.append(c);
            } else {
                readingNumber = false;
                unit.append(c);
            }
        }

        if (readingNumber) {
            throw new TimeParseException("Number \"" + number + "\" not matched with unit at end of string");
        } else {
            totalMilliseconds += parseTimeComponent(number.toString(), unit.toString());
        }

        return new Time(totalMilliseconds);
    }

    private static double parseTimeComponent(String magnitudeString, String unit) throws TimeParseException {
        if (magnitudeString.isEmpty()) {
            throw new TimeParseException("Missing number for unit \"" + unit + "\"");
        }

        long magnitude;

        try {
            magnitude = Long.valueOf(magnitudeString);
        } catch(NumberFormatException e) {
            throw new TimeParseException("Unable to parse number \"" + magnitudeString + "\"", e);
        }

        unit = unit.toLowerCase();

        if (unit.length() > 3 && unit.substring(unit.length() - 3).equals("and")) {
            unit = unit.substring(0, unit.length() - 3);
        }

        Long unitMultiplier = unitMultipliers.get(unit);

        if (unitMultiplier == null) {
            throw new TimeParseException("Unknown time unit \"" + unit + "\"");
        }

        return magnitude * unitMultiplier;
    }

    private static long appendTime(long time, long unitInMS, String name, StringBuilder builder) {
        long timeInUnits = (time - (time % unitInMS)) / unitInMS;

        if (timeInUnits > 0) {
            builder.append(timeInUnits).append(' ').append((timeInUnits > 1 ? name + "s" : name)).append(' ');
        }

        return time - timeInUnits * unitInMS;
    }

    public static String toString(long time) {
        StringBuilder timeString = new StringBuilder();

        time = appendTime(time, YEAR_MS, Messages.TIME_YEAR.get(), timeString);
        time = appendTime(time, DAY_MS, Messages.TIME_DAY.get(), timeString);
        time = appendTime(time, HOUR_MS, Messages.TIME_HOUR.get(), timeString);
        time = appendTime(time, MINUTE_MS, Messages.TIME_MINUTE.get(), timeString);
        time = appendTime(time, SECOND_MS, Messages.TIME_SECOND.get(), timeString);

        if (timeString.length() == 0) {
            if (time == 0) {
                return "0 " + Messages.TIME_SECOND.get() + "s";
            } else {
                return time + " ms";
            }
        }

        return timeString.toString().trim();
    }

    public static class TimeParseException extends RuntimeException {

        public TimeParseException(String reason) {
            super(reason);
        }

        public TimeParseException(String reason, Throwable cause) {
            super(reason, cause);
        }

    }
}