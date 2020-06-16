package com.larkea.boot.core.util;

import java.math.BigInteger;
import java.util.UUID;

public class IdUtil {

    private IdUtil() {
    }

    /**
     * 生成标准的UUID字符串
     *
     * @return UUID 字符串，带有减号分隔
     */
    public static String uuid() {
        return randomUuid().toString();
    }

    /**
     * 生成 base62 的 UUID 字符串
     *
     * @return base62 字符串
     */
    public static String base62Uuid() {
        return Base62Util.encode(bigInteger(randomUuid()));
    }


    /**
     * 生成标准 UUID
     *
     * @return UUID 对象
     */
    public static UUID randomUuid() {
        return UUID.randomUUID();
    }

    /**
     * 生成不带减号分隔的UUID字符串
     *
     * @return 不带减号分隔的UUID字符串
     */
    public static String simpleUuid() {
        UUID uuid = randomUuid();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        return (digits(mostSigBits >> 32, 8) +
                digits(mostSigBits >> 16, 4) +
                digits(mostSigBits, 4) +
                digits(leastSigBits >> 48, 4) +
                digits(leastSigBits, 12));
    }


    public static UUID uuid(String base62Uuid) {
        BigInteger decoded = Base62Util.decode(base62Uuid);
        return uuid(decoded);
    }

    public static UUID uuid(BigInteger value) {
        BigInteger[] unpaired = BigIntegerUtil.unpair(value);
        return new UUID(unpaired[0].longValueExact(), unpaired[1].longValueExact());
    }

    public static BigInteger bigInteger(UUID id) {
        return BigIntegerUtil.pair(
                BigInteger.valueOf(id.getMostSignificantBits()),
                BigInteger.valueOf(id.getLeastSignificantBits())
        );
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
}
