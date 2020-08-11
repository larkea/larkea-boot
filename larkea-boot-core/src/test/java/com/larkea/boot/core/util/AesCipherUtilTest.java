package com.larkea.boot.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author cuiwentao
 * @since 2020/8/11 11:03
 */
public class AesCipherUtilTest {

	@Test
	void encryptTest() {
		String raw = "{\"data\":null}";
		String key = RandomUtil.randomString(RandomUtil.STRING, 6);
		System.out.println("Raw=" + raw);
		System.out.println("Key=" + key);
		String enc = AesCipherUtil.encrypt(raw, key);
		System.out.println("Enc=" + enc);

		String rawBack = AesCipherUtil.decrypt(enc, key);
		System.out.println("Raw=" + rawBack);
		assertEquals(rawBack, raw);
	}
}
