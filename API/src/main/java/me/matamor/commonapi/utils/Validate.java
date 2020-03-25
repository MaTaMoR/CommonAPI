package me.matamor.commonapi.utils;

public class Validate {

    public static void notNull(Object object, String error) {
        if (object == null) {
            throw new NullPointerException(error);
        }
    }

    public static void isTrue(boolean statement, String error) {
        if (!statement) {
            throw new IllegalArgumentException(error);
        }
    }

    public static void ifTrue(boolean statement, String error) {
        if (statement) {
            throw new IllegalArgumentException(error);
        }
    }

    public static void isFalse(boolean statement, String error) {
        if (statement) {
            throw new IllegalArgumentException(error);
        }
    }

    public static void ifFalse(boolean statement, String error) {
        if (!statement) {
            throw new IllegalArgumentException(error);
        }
    }
}
