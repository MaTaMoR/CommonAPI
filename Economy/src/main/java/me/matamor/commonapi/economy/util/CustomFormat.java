package me.matamor.commonapi.economy.util;

public class CustomFormat {

    public static String format(double balance, String major, String minor) {
        if (balance < 0.01) {
            return "0 " + minor;
        }

        String stringBalance = String.valueOf(balance);
        String[] args = stringBalance.split("\\.");

        StringBuilder stringBuilder = new StringBuilder();

        if (balance > 1) {
            stringBuilder.append(args[0]).append(" ").append(major);
        }

        if (args.length == 2) {
            String decimal = args[1];

            int maxLength = decimal.length();
            if (maxLength > 2) {
                maxLength = 2;
            }

            decimal = decimal.substring(0, maxLength);

            if (!decimal.equals("0") && !decimal.equals("00")) {
                if (decimal.length() == 1) {
                    decimal = decimal + "0";
                }

                if (decimal.startsWith("0")) {
                    decimal = decimal.substring(1);
                }

                stringBuilder.append(" ").append(decimal).append(" ").append(minor);
            }
        }

        return stringBuilder.toString().trim();
    }
}