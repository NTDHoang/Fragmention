package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;

public final class StringUtils {

    private StringUtils() {
        // No-op
    }

    public static boolean isBlank(String str) {
        return str == null || TextUtils.isEmpty(str.trim()) || str.equalsIgnoreCase("null");
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String getString(Context context, @StringRes int stringId) {
        return context.getResources().getString(stringId);
    }

    public static String trim(String input) {
        if (TextUtils.isEmpty(input)) {
            return input;
        }
        String output = input.trim();
        while (output.startsWith("\u3000")) {
            output = output.substring(1, output.length());
        }

        while (output.endsWith("\u3000")) {
            output = output.substring(0, output.length() - 1);
        }

        return output;
    }

    public static float convertToFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int convertToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
