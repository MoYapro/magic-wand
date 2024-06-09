package de.moyapro.colors.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class HashUuidFunctions {

    private static final int V3 = 3; // MD5
    private static final int V5 = 5; // SHA-1

    private static final String HASH_V3 = "MD5";
    private static final String HASH_V5 = "SHA-1";

    public static final UUID NAMESPACE_DNS = new UUID(0x6ba7b8109dad11d1L, 0x80b400c04fd430c8L);
    public static final UUID NAMESPACE_URL = new UUID(0x6ba7b8119dad11d1L, 0x80b400c04fd430c8L);
    public static final UUID NAMESPACE_OID = new UUID(0x6ba7b8129dad11d1L, 0x80b400c04fd430c8L);
    public static final UUID NAMESPACE_X500 = new UUID(0x6ba7b8149dad11d1L, 0x80b400c04fd430c8L);

    public static UUID v3(String name) {
        return generate(V3, HASH_V3, null, name);
    }

    public static UUID v5(String name) {
        return generate(V5, HASH_V5, null, name);
    }

    public static UUID v3(UUID namespace, String name) {
        return generate(V3, HASH_V3, namespace, name);
    }

    public static UUID v5(UUID namespace, String name) {
        return generate(V5, HASH_V5, namespace, name);
    }

    private static UUID generate(int version, String algorithm, UUID namespace, String name) {

        MessageDigest hasher = hasher(algorithm);

        if (namespace != null) {
            ByteBuffer ns = ByteBuffer.allocate(16);
            ns.putLong(namespace.getMostSignificantBits());
            ns.putLong(namespace.getLeastSignificantBits());
            hasher.update(ns.array());
        }

        hasher.update(name.getBytes(StandardCharsets.UTF_8));
        ByteBuffer hash = ByteBuffer.wrap(hasher.digest());

        final long msb = (hash.getLong() & 0xffffffffffff0fffL) | (version & 0x0f) << 12;
        final long lsb = (hash.getLong() & 0x3fffffffffffffffL) | 0x8000000000000000L;

        return new UUID(msb, lsb);
    }

    private static MessageDigest hasher(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("%s not supported.", algorithm));
        }
    }
}
