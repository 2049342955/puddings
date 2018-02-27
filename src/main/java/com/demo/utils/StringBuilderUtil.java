package com.demo.utils;

public class StringBuilderUtil {
    public static final String EMPTY_STRING = "";

    public StringBuilderUtil() {
    }

    public static String append(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] var2 = strings;
        int var3 = strings.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String string = var2[var4];
            if (string != null) {
                stringBuilder.append(string);
            }
        }

        return stringBuilder.toString();
    }

    public static String appendWithSplitor(String splitor, String... strings) {
        if (splitor == null) {
            splitor = "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        String[] var3 = strings;
        int var4 = strings.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String string = var3[var5];
            stringBuilder.append(string).append(splitor);
        }

        return stringBuilder.substring(0, stringBuilder.length() - splitor.length());
    }
}
