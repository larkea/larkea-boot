package com.larkea.boot.core.util;

public class CastUtil {

    private CastUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }
}
