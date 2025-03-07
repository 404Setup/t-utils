package one.tranic.t.utils;

import java.lang.reflect.Modifier;

/**
 * Utility class containing reflection-related methods for manipulating and inspecting classes and fields.
 */
@SuppressWarnings("unused")
public class Reflect {
    /**
     * Assigns a value to a static field in the specified class if the field is currently uninitialized (null).
     *
     * @param targetClass the class containing the static field.
     * @param fieldName   the name of the static field to assign the value to.
     * @param value       the value to assign to the static field if uninitialized.
     * @throws IllegalArgumentException if the specified field is not static.
     * @throws RuntimeException         if the field cannot be accessed or assigned.
     */
    public static void assignToStaticFieldIfUninitialized(Class<?> targetClass, String fieldName, Object value, boolean debug) throws NoSuchFieldException, IllegalAccessException {
        var field = targetClass.getDeclaredField(fieldName);

        if (!Modifier.isStatic(field.getModifiers()))
            throw new IllegalArgumentException("Field " + fieldName + " is not static.");
        field.setAccessible(true);
        if (field.get(null) != null) return;
        field.set(null, value);
    }

    /**
     * Checks whether a class with the specified fully qualified name exists in the classpath.
     *
     * @param className the fully qualified name of the class to check.
     * @return {@code true} if the class exists; {@code false} otherwise.
     */
    public static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Retrieves a {@code Class} object associated with the fully qualified name of a class.
     *
     * @param className the fully qualified name of the class to retrieve.
     * @return the {@code Class} object for the given name, or {@code null} if the class cannot be found.
     */
    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
