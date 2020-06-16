package com.larkea.boot.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StackTraceUtil {

    private StackTraceUtil() {
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

}
