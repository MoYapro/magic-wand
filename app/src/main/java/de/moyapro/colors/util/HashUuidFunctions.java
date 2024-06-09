package de.moyapro.colors.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class HashUuidFunctions {

    private static final int V5 = 5; // SHA-1

    private static final String HASH_V5 = "SHA-1";

    public static UUID v5(String name) {
        return generate(name);
    }

    private static UUID generate(String name) {

        MessageDigest hasher = hasher();

        hasher.update(name.getBytes(StandardCharsets.UTF_8));
        ByteBuffer hash = ByteBuffer.wrap(hasher.digest());

        final long msb = (hash.getLong() & 0xffffffffffff0fffL) | (HashUuidFunctions.V5 & 0x0f) << 12;
        final long lsb = (hash.getLong() & 0x3fffffffffffffffL) | 0x8000000000000000L;

        return new UUID(msb, lsb);
    }

    private static MessageDigest hasher() {
        try {
            return MessageDigest.getInstance(HashUuidFunctions.HASH_V5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("%s not supported.", HashUuidFunctions.HASH_V5));
        }
    }
}
