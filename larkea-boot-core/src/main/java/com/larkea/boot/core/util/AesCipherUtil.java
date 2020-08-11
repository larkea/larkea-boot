package com.larkea.boot.core.util;

import java.util.Base64;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cuiwentao
 * @since 2020/8/10 18:39
 */
@Slf4j
public class AesCipherUtil {

	public static final int BLOCK_SIZE = 16;

	public static String encrypt(String raw, String key) {
		return Optional.ofNullable(encrypt(raw.getBytes(), key))
				.map(String::new).orElse(null);
	}

	public static byte[] encrypt(byte[] raw, String key) {
		byte[] iv = RandomUtil.randomString(BLOCK_SIZE).getBytes();
		byte[] key256 = sha256(key);
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key256, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
			byte[] encrypted = cipher.doFinal(raw);
			byte[] data = new byte[BLOCK_SIZE + encrypted.length];

			System.arraycopy(iv, 0, data, 0, iv.length);
			System.arraycopy(encrypted, 0, data, iv.length, encrypted.length);
			return Base64.getEncoder().encode(data);
		} catch (Exception e) {
			return null;
		}
	}

	public static String decrypt(String enc, String key) {
		return Optional.ofNullable(decrypt(enc.getBytes(), key))
				.map(String::new).orElse(null);
	}

	public static byte[] decrypt(byte[] enc, String key) {
		byte[] key256 = sha256(key);
		byte[] data = Base64.getDecoder().decode(enc);
		byte[] iv = new byte[BLOCK_SIZE];
		byte[] encrypted = new byte[data.length - iv.length];
		System.arraycopy(data, 0, iv, 0, iv.length);
		System.arraycopy(data, iv.length, encrypted, 0, encrypted.length);
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key256, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
			return cipher.doFinal(encrypted);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] sha256(String str) {
		return Hashing.sha256().hashBytes(str.getBytes()).asBytes();
	}
}
