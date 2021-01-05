package com.larkea.boot.core.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class NumberUtilTest {

    @Test
    public void toLong() {
        Long lLong = 123456L;
        String sLong = "123456";
        assertEquals(lLong, NumberUtil.toLong(sLong));

        assertEquals(lLong, NumberUtil.toLong(lLong));

        assertEquals(NumberUtil.toLong(sLong).longValue(), NumberUtil.toLong(lLong).longValue());

        Long nullLong = null;
        assertNull(NumberUtil.toLong(nullLong));
    }

}