package com.larkea.boot.core.util;

public class MaskUtil {

    private MaskUtil() {
    }

    public static String maskMobile(String mobile) {
        if (mobile == null || mobile.length() < 11) {
            return mobile;
        }

        return new StringBuilder(mobile.substring(0, 3))
                .append("****")
                .append(mobile.substring(7))
                .toString();
    }
}
