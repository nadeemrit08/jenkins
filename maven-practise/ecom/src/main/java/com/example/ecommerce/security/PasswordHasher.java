package com.example.ecommerce.security;

import com.example.ecommerce.exception.DataAccessException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordHasher {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final String PREFIX = "pbkdf2";
    private static final int DEFAULT_ITERATIONS = 65_536;
    private static final int SALT_BYTES = 16;
    private final SecureRandom secureRandom = new SecureRandom();

    public boolean verify(char[] rawPassword, String storedHash) {
        String[] parts = storedHash.split("\\$");
        if (parts.length != 4 || !PREFIX.equals(parts[0])) {
            return false;
        }

        int iterations = Integer.parseInt(parts[1]);
        byte[] salt = Base64.getDecoder().decode(parts[2]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[3]);
        byte[] actualHash = hash(rawPassword, salt, iterations, expectedHash.length);
        return MessageDigest.isEqual(expectedHash, actualHash);
    }

    public String hashForStorage(char[] rawPassword, byte[] salt, int iterations) {
        byte[] hash = hash(rawPassword, salt, iterations, 32);
        return PREFIX + "$" + iterations + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    public String hashForStorage(char[] rawPassword) {
        byte[] salt = new byte[SALT_BYTES];
        secureRandom.nextBytes(salt);
        return hashForStorage(rawPassword, salt, DEFAULT_ITERATIONS);
    }

    private byte[] hash(char[] rawPassword, byte[] salt, int iterations, int outputBytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(rawPassword, salt, iterations, outputBytes * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new DataAccessException("Password verification failed", e);
        }
    }
}
