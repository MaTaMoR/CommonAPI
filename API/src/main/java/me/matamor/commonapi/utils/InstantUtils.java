package me.matamor.commonapi.utils;

import java.time.Duration;

public final class InstantUtils {

    private InstantUtils() {

    }

    public static String humanReadableFormat(Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }
}
