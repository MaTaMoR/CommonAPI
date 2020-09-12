package me.matamor.commonapi.utils;

public final class SimpleMath {

    private SimpleMath() {

    }


    public static double square(double num) {
        return num * num;
    }

    public static int floor(double num) {
        int floor = (int) num;
        return floor == num ? floor : floor - (int) (Double.doubleToRawLongBits(num) >>> 63);
    }
}
