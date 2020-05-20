package com.larkea.boot.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NumberUtilTest {

	@org.junit.jupiter.api.Test
	void toLong() {
		Long lLong = 123456L;
		String sLong = "123456";
		assertEquals(lLong, NumberUtil.toLong(sLong));

		assertEquals(lLong, NumberUtil.toLong(lLong));

		assertEquals(NumberUtil.toLong(sLong).longValue(), NumberUtil.toLong(lLong).longValue());

		Long nullLong = null;
		assertNull(NumberUtil.toLong(nullLong));
	}

}