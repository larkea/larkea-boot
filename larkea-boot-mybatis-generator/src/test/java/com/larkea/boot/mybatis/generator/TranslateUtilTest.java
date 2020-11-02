package com.larkea.boot.mybatis.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslateUtilTest {

	@Test
	void plural() {
		// 单数转复数
		assertEquals("waters", TranslateUtil.pluralize("water"));
		assertEquals("boxes", TranslateUtil.pluralize("box"));
		assertEquals("tomatoes", TranslateUtil.pluralize("tomato"));
		assertEquals("news", TranslateUtil.pluralize("news"));


		// 复数转单数
		assertEquals("apple", TranslateUtil.singularize("apples"));
	}
}