package one.pkg.tiny.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Modifier;

/**
 * Utility class containing reflection-related methods for manipulating and inspecting classes and fields.
 */
@SuppressWarnings("unused")
public class Reflect {
    public static void enforceType(@NotNull Class<?> field, @NotNull Class<?> value) {
        if (!field.isAssignableFrom(value))
            throw new IllegalArgumentException(
                    String.format("Field type mismatch: expected %s, actual %s",
                            field.getName(), value.getName()));
    }

    /**
     * Sets the value of a static field in the specified class if the field's current value is null.
     *
     * @param targetClass the class containing the static field. Must not be null.
     * @param fieldName   the name of the static field to set. Must not be null.
     * @param value       the value to assign to the static field. Must not be null.
     * @throws NoSuchFieldException     if the field with the specified name does not exist in the class.
     * @throws IllegalAccessException   if the field cannot be accessed or modified due to security restrictions.
     * @throws IllegalArgumentException if the field is not static.
     */
    public static void setNotNullStaticField(@NotNull Class<?> targetClass, @NotNull String fieldName, @NotNull Object value) throws NoSuchFieldException, IllegalAccessException {
        var field = targetClass.getDeclaredField(fieldName);

        if (!Modifier.isStatic(field.getModifiers()))
            throw new IllegalArgumentException("Field " + fieldName + " is not static.");
        enforceType(field.getType(), value.getClass());
        field.setAccessible(true);
        if (field.get(null) != null) return;
        field.set(null, value);
    }

    /**
     * Sets a non-null static field on the specified class with the provided value.
     * This method ensures that the field being set is not null and matches the given
     * field name and type. If the field does not exist or cannot be accessed, an exception is thrown.
     *
     * @param targetClass the class containing the static field to be set
     * @param fieldName   the name of the static field to be modified
     * @param value       the value to assign to the static field, which must not be null
     * @throws NoSuchFieldException   if the specified field does not exist
     * @throws IllegalAccessException if the field is not accessible
     */
    public static void setNotNullStaticFieldL(@NotNull Class<?> targetClass, @NotNull String fieldName, @NotNull Object value) throws NoSuchFieldException, IllegalAccessException {
        var lookup = getLookup(targetClass);

        setNotNullStaticFieldL(lookup, targetClass, fieldName, value);
    }

    /**
     * Sets a static field of the specified class to a non-null value if it is currently null.
     *
     * @param lookup      the MethodHandles.Lookup instance to perform reflective operations
     * @param targetClass the class containing the static field to set
     * @param fieldName   the name of the static field
     * @param value       the non-null value to set the field to
     * @throws NoSuchFieldException   if the specified field does not exist
     * @throws IllegalAccessException if accessing the field is not permitted
     */
    public static void setNotNullStaticFieldL(@NotNull MethodHandles.Lookup lookup, @NotNull Class<?> targetClass, @NotNull String fieldName, @NotNull Object value) throws NoSuchFieldException, IllegalAccessException {
        var field = lookup.findStaticVarHandle(targetClass, fieldName, value.getClass());
        if (field.get() != null) return;
        field.set(value);
    }

    /**
     * Sets the value of a static field in the specified class using reflection.
     *
     * @param targetClass the class containing the static field. Must not be null.
     * @param fieldName   the name of the static field to set. Must not be null.
     * @param value       the value to assign to the static field. Must not be null.
     * @throws NoSuchFieldException     if the field with the specified name does not exist in the class.
     * @throws IllegalAccessException   if the field cannot be accessed or modified due to security restrictions.
     * @throws IllegalArgumentException if the field is not static.
     */
    public static void setStaticField(@NotNull Class<?> targetClass, @NotNull String fieldName, @NotNull Object value) throws NoSuchFieldException, IllegalAccessException {
        var field = targetClass.getDeclaredField(fieldName);

        if (!Modifier.isStatic(field.getModifiers()))
            throw new IllegalArgumentException("Field " + fieldName + " is not static.");
        enforceType(field.getType(), value.getClass());
        field.setAccessible(true);
        field.set(null, value);
    }

    /**
     * Sets the value of a static field in the specified class using reflection.
     *
     * @param targetClass the class containing the static field. Must not be null.
     * @param fieldName   the name of the static field to modify. Must not be null.
     * @param value       the value to assign to the static field. Must not be null.
     * @throws NoSuchFieldException   if the field with the specified name does not exist in the class.
     * @throws IllegalAccessException if the field cannot be accessed or modified due to security restrictions.
     */
    public static void setStaticFieldL(@NotNull Class<?> targetClass, @NotNull String fieldName, @NotNull Object value) throws NoSuchFieldException, IllegalAccessException {
        var lookup = getLookup(targetClass);
        setStaticFieldL(lookup, targetClass, fieldName, value);
    }

    /**
     * Sets the value of a static field in the specified class.
     *
     * @param lookup      the MethodHandles.Lookup object used to access the field
     * @param targetClass the Class object representing the class containing the static field
     * @param fieldName   the name of the static field to be set
     * @param value       the value to set to the static field
     * @throws NoSuchFieldException   if the specified field does not exist in the target class
     * @throws IllegalAccessException if access to the field is denied
     */
    public static void setStaticFieldL(@NotNull MethodHandles.Lookup lookup, @NotNull Class<?> targetClass, @NotNull String fieldName, @NotNull Object value) throws NoSuchFieldException, IllegalAccessException {
        var field = lookup.findStaticVarHandle(targetClass, fieldName, value.getClass());
        field.set(value);
    }

    /**
     * Sets the value of a field in an object instance using reflection.
     *
     * @param targetObject the object instance containing the field to modify. Must not be null.
     * @param fieldName    the name of the field to modify. Must not be null.
     * @param value        the value to assign to the field. Must not be null.
     * @throws NoSuchFieldException   if the field with the specified name does not exist in the class.
     * @throws IllegalAccessException if the field cannot be accessed or modified due to security restrictions.
     */
    public static void setFieldL(@NotNull Object targetObject, @NotNull String fieldName, @NotNull Object value) throws NoSuchFieldException, IllegalAccessException {
        var lookup = getLookup(targetObject.getClass());
        setFieldL(lookup, targetObject, fieldName, value);
    }

    /**
     * Sets the value of the specified field on the given target object.
     *
     * @param lookup       the {@code MethodHandles.Lookup} object used to perform the operation
     * @param targetObject the target object whose field value is to be set
     * @param fieldName    the name of the field to be set
     * @param value        the new value to be assigned to the field
     * @throws NoSuchFieldException   if the specified field does not exist
     * @throws IllegalAccessException if the field is not accessible
     */
    public static void setFieldL(@NotNull MethodHandles.Lookup lookup, @NotNull Object targetObject, @NotNull String fieldName, @NotNull Object value) throws NoSuchFieldException, IllegalAccessException {
        var field = lookup.findVarHandle(targetObject.getClass(), fieldName, value.getClass());
        field.set(targetObject, value);
    }

    /**
     * Sets the value of a specified field in the given target object.
     *
     * @param targetObject the object whose field is to be modified. Must not be null.
     * @param fieldName    the name of the field to modify. Must not be null.
     * @param value        the new value to set for the specified field. Must not be null.
     * @throws NoSuchFieldException   if the specified field is not found in the target object's class.
     * @throws IllegalAccessException if the field cannot be accessed or modified due to security restrictions.
     */
    public static void setField(@NotNull Object targetObject, @NotNull String fieldName, @NotNull Object value) throws NoSuchFieldException, IllegalAccessException {
        var field = targetObject.getClass().getDeclaredField(fieldName);
        enforceType(field.getType(), value.getClass());

        field.setAccessible(true);
        field.set(targetObject, value);
    }

    /**
     * Retrieves the value of a static field from the specified class using reflection.
     *
     * @param targetClass the class containing the static field. Must not be null.
     * @param fieldName   the name of the static field to retrieve. Must not be null.
     * @return the value of the specified static field.
     * @throws NoSuchFieldException     if the field with the specified name does not exist in the class.
     * @throws IllegalAccessException   if the field cannot be accessed due to security restrictions.
     * @throws IllegalArgumentException if the field is not static.
     */
    public static Object getStaticField(@NotNull Class<?> targetClass, @NotNull String fieldName) throws IllegalAccessException, NoSuchFieldException {
        var field = targetClass.getDeclaredField(fieldName);
        if (!Modifier.isStatic(field.getModifiers()))
            throw new IllegalArgumentException("Field " + fieldName + " is not static.");
        field.setAccessible(true);
        return field.get(null);
    }

    /**
     * Retrieves the value of a specified field from an object instance using reflection.
     *
     * @param target    the object instance containing the field. Must not be null.
     * @param fieldName the name of the field to retrieve. Must not be null.
     * @param arg       the instance on which to retrieve the value. Must not be null.
     * @return the value of the specified field.
     * @throws NoSuchFieldException   if the field with the specified name does not exist in the object's class.
     * @throws IllegalAccessException if the field cannot be accessed due to security restrictions.
     */
    public static Object getField(@NotNull Object target, @NotNull String fieldName, @NotNull Object arg) throws NoSuchFieldException, IllegalAccessException {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(arg);
    }

    /**
     * Retrieves the value of the specified field in the given target object using reflection.
     *
     * @param targetObject the object instance from which the field value is retrieved. Must not be null.
     * @param fieldName    the name of the field to retrieve. Must not be null.
     * @param fieldType    the type of the field to retrieve. Must not be null.
     * @param arg          an argument specifying additional context for accessing the field. Must not be null.
     * @return the value of the specified field.
     * @throws NoSuchFieldException   if the field with the specified name does not exist in the class.
     * @throws IllegalAccessException if the field cannot be accessed due to security restrictions.
     */
    public static Object getField(@NotNull Object targetObject, @NotNull String fieldName, @NotNull Class<?> fieldType, @NotNull Object arg) throws NoSuchFieldException, IllegalAccessException {
        var lookup = getLookup(targetObject.getClass());
        return getField(lookup, targetObject, fieldName, fieldType, arg);
    }

    /**
     * Retrieves the value of a field in an object instance using {@link MethodHandles} for reflection.
     *
     * @param lookup       a {@link MethodHandles.Lookup} instance for accessing the field. Must not be null.
     * @param targetObject the object instance containing the field to retrieve. Must not be null.
     * @param fieldName    the name of the field to retrieve. Must not be null.
     * @param fieldType    the type of the field to retrieve. Must not be null.
     * @param arg          the argument required by the field getter. Must not be null.
     * @return the value of the specified field.
     * @throws NoSuchFieldException   if the field with the specified name does not exist in the class.
     * @throws IllegalAccessException if the field cannot be accessed due to security restrictions.
     */
    public static Object getField(@NotNull MethodHandles.Lookup lookup, @NotNull Object targetObject, @NotNull String fieldName, @NotNull Class<?> fieldType, @NotNull Object arg) throws NoSuchFieldException, IllegalAccessException {
        var field = lookup.findVarHandle(targetObject.getClass(), fieldName, fieldType);
        return field.get(arg);
    }

    /**
     * Retrieves the value of a static field from the specified class using reflection and method handles.
     *
     * @param targetClass the class containing the static field. Must not be null.
     * @param fieldName   the name of the static field to retrieve. Must not be null.
     * @param fieldType   the type of the static field to retrieve. Must not be null.
     * @return the value of the specified static field.
     * @throws NoSuchFieldException   if the field with the specified name does not exist in the class.
     * @throws IllegalAccessException if the field cannot be accessed due to security restrictions.
     */
    public static Object getStaticField(@NotNull Class<?> targetClass, @NotNull String fieldName, @NotNull Class<?> fieldType) throws NoSuchFieldException, IllegalAccessException {
        var lookup = getLookup(targetClass);
        return getStaticField(lookup, targetClass, fieldName, fieldType);
    }

    /**
     * Retrieves the value of a static field from the specified class using {@link MethodHandles}.
     *
     * @param lookup      a {@link MethodHandles.Lookup} instance for accessing the field. Must not be null.
     * @param targetClass the class containing the static field. Must not be null.
     * @param fieldName   the name of the static field to retrieve. Must not be null.
     * @param fieldType   the type of the static field. Must not be null.
     * @return the value of the specified static field.
     * @throws NoSuchFieldException   if the field with the specified name does not exist in the class.
     * @throws IllegalAccessException if the field cannot be accessed due to security restrictions.
     */
    public static Object getStaticField(@NotNull MethodHandles.Lookup lookup, @NotNull Class<?> targetClass, @NotNull String fieldName, @NotNull Class<?> fieldType) throws NoSuchFieldException, IllegalAccessException {
        var field = lookup.findStaticVarHandle(targetClass, fieldName, fieldType);
        return field.get();
    }

    /**
     * Retrieves a {@link MethodHandles.Lookup} instance for performing operations on the specified class.
     *
     * @param targetClass the class for which the {@link MethodHandles.Lookup} instance is created.
     *                    Must not be null.
     * @return a {@link MethodHandles.Lookup} instance for accessing private or otherwise inaccessible
     * members of the specified class.
     * @throws IllegalAccessException if the attempt to create the {@link MethodHandles.Lookup} instance
     *                                violates security rules.
     */
    public static MethodHandles.Lookup getLookup(@NotNull Class<?> targetClass) throws IllegalAccessException {
        var lookup1 = MethodHandles.lookup();
        return MethodHandles.privateLookupIn(targetClass, lookup1);
    }

    /**
     * Checks whether a class with the specified fully qualified name exists in the classpath.
     *
     * @param className the fully qualified name of the class to check.
     * @return {@code true} if the class exists; {@code false} otherwise.
     */
    public static boolean hasClass(@NotNull String className) {
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
    public static @Nullable Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
