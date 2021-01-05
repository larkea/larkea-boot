package com.larkea.boot.core.util;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilTest {

	@Test
	public void timeConstantEquals() {

		String s1 = null, s2 = null;

		assertTrue(StringUtil.timeConstantEquals(s1, s2));

		s1 = "test";
		assertFalse(StringUtil.timeConstantEquals(s1, s2));

		s2 = "p2";
		assertFalse(StringUtil.timeConstantEquals(s1, s2));

		s2 = "test";
		assertTrue(StringUtil.timeConstantEquals(s1, s2));
	}
}