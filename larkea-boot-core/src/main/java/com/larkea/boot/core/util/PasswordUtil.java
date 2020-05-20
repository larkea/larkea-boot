package com.larkea.boot.core.util;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class PasswordUtil {

	private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

	private static final int PBKDF2_ITERATIONS = 1000;

	public static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		return skf.generateSecret(spec).getEncoded();
	}

	public static String pbkdf2(@NonNull String password, @NonNull String salt, int iterations,
			int bytes) {
		if (password == null || salt == null) {
			throw new IllegalArgumentException("Password or salt cannot be null");
		}

		try {
			return HexUtil.encode(
					pbkdf2(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), iterations, bytes));
		}
		catch (Exception e) {
			LOGGER.warn("password length:{},salt:{},iterations:{},bytes:{}", password.length(), salt,
					iterations, bytes, e);
			throw new IllegalArgumentException("Generate password hash failed", e);
		}
	}

	public static String pbkdf2(@NonNull String password, @NonNull String salt) {
		return pbkdf2(password, salt, PBKDF2_ITERATIONS, salt.length());
	}
}
