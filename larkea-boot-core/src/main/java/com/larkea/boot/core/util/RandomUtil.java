package com.larkea.boot.core.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomUtil {
    public static final String DIGITS = "0123456789";
    public static final String HEX = "0123456789ABCDEF";
    public static final String ASCII_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String PUNCTUATION = "!\"#$%&\\'()*+,-./:;<=>?@[\\\\]^_`{|}~";
    public static final String STRING = DIGITS + ASCII_LETTERS;
    public static final String VISIBLE_CHARS = DIGITS + ASCII_LETTERS + PUNCTUATION;

    private RandomUtil() {
    }

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    public static SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Cannot get SecureRandom Instance", e);
            return null;
        }
    }

    public static int randomInt() {
        return getRandom().nextInt();
    }

    public static int randomInt(int bound) {
        return getRandom().nextInt(bound);
    }

    public static int randomInt(int origin, int bound) {
        return getRandom().nextInt(origin, bound);
    }

    public static String randomString(int length) {
        return randomString(STRING, length);
    }

    public static String randomDigit(int length) {
        return randomString(DIGITS, length);
    }

    public static String randomHex(int length) {
        return randomString(HEX, length);
    }

    public static String randomSalt(int length) {
        return randomString(VISIBLE_CHARS, length);
    }

    public static byte[] randomBytes(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot less than 0, now is " + length);
        }
        byte[] bytes = new byte[length];
        getRandom().nextBytes(bytes);
        return bytes;
    }

    public static String randomString(@NonNull String baseString, int length) {
        final StringBuilder sb = new StringBuilder();

        if (length < 0) {
            throw new IllegalArgumentException("length cannot less than 0, now is " + length);
        }

        int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            int number = getRandom().nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }
}
