package one.tranic.t.utils;

import org.jetbrains.annotations.Range;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public class Rand {
    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Generates a random alphanumeric string of the specified length.
     *
     * @param length the length of the generated string. Must be between 1 and 56, inclusive.
     * @return a randomly generated string of the specified length.
     * @throws IllegalArgumentException if the length is not between 1 and 56.
     */
    @SuppressWarnings("all")
    public static String generateRandomId(@Range(from = 1, to = 56) int length) throws IllegalArgumentException {
        if (length < 1 || length > 56) {
            throw new IllegalArgumentException("Length must be between 1 and 56.");
        }

        char[] id = new char[length];
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < length; i++) {
            id[i] = chars.charAt(random.nextInt(chars.length()));
        }
        return new String(id);
    }
}
