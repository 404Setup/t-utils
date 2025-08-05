package one.pkg.tiny.utils;

public class Validate {
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object obj) {
        notNull(obj, "Object must not be null");
    }
}
