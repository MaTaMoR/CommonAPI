package me.matamor.commonapi.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class StringUtils {

    private StringUtils() {

    }

    public static String color(String text) {
        Validate.notNull(text, "Text can't be null!");

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> color(Collection<String> collection) {
        Validate.notNull(collection, "Collection can't be null!");

        return collection.stream().map(StringUtils::color).collect(Collectors.toList());
    }

    public static void sendMessage(CommandSender commandSender, String message) {
        String[] split = message.split("\\{new_line}");

        for (String arg : split) {
            commandSender.sendMessage(color(arg));
        }
    }

    public static void sendMessageRaw(CommandSender commandSender, String message) {
        String[] split = message.split("\\{new_line}");

        for (String arg : split) {
            commandSender.sendMessage(arg);
        }
    }

    public static String replaceNonAlphanumeric(String text) {
        return text.replaceAll("[^A-Za-z0-9-&]", "");
    }

    public static String toString(Object... array) {
        return toString(Object::toString, array);
    }

    @SafeVarargs
    public static <T> String toString(Function<T, String> toStringFunction, T... array) {
        if (array == null) {
            return "null";
        }

        int iMax = array.length - 1;
        if (iMax == -1) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (int i = 0; ; i++) {
            stringBuilder.append(toStringFunction.apply(array[i]));

            if (i == iMax) {
                return stringBuilder.append(']').toString();
            }

            stringBuilder.append(", ");
        }
    }

    public static String toString(List<Object> array) {
        if (array == null) {
            return "null";
        }

        int iMax = array.size() - 1;
        if (iMax == -1) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (int i = 0; ; i++) {
            stringBuilder.append(array.get(i));

            if (i == iMax) {
                return stringBuilder.append(']').toString();
            }

            stringBuilder.append(", ");
        }
    }

    public static <T> String toString(Function<T, String> toStringFunction, Collection<T> collection) {
        if (collection == null) {
            return "null";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(toStringFunction.apply(iterator.next()))
                    .append((iterator.hasNext() ? ", " : "]"));
        }

        return stringBuilder.toString();
    }

    public static String normalizeEnum(String text) {
        if (text.contains("_")) {
            StringBuilder stringBuilder = new StringBuilder();

            for (String arg : text.split("_")) {
                stringBuilder.append(arg.substring(0, 1).toUpperCase()).append(arg.substring(1).toLowerCase());
            }

            return stringBuilder.toString().trim();
        } else {
            return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        }
    }
}
