package one.pkg.tiny.utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    private static final MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] hash(byte[] data) {
        return digest.digest(data);
    }

    public static byte[] hash(@NotNull String data) {
        Validate.notNull(data, "Data must not be null");
        return hash(data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] hash(@NotNull File file) throws Exception {
        Validate.notNull(file, "File must not be null");
        return hash(file.toPath());
    }

    public static byte[] hash(@NotNull Path file) throws Exception {
        Validate.notNull(file, "File must not be null");
        if (!file.toFile().exists()) throw new IllegalArgumentException("File does not exist: " + file);
        if (!file.toFile().isFile()) throw new IllegalArgumentException("File is not a file: " + file);
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file))) {
            byte[] buffer = new byte[65536];
            int bytesRead;

            while ((bytesRead = bis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            return digest.digest();
        } finally {
            digest.reset();
        }
    }

    public static String format(byte[] hash) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
