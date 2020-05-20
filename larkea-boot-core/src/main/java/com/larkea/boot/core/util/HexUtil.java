package com.larkea.boot.core.util;

import com.google.common.io.BaseEncoding;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HexUtil {

	public static byte[] decode(String hex) {
		return BaseEncoding.base16().decode(hex);
	}

	public static String encode(byte[] bytes) {
		return BaseEncoding.base16().lowerCase().encode(bytes);
	}
}
