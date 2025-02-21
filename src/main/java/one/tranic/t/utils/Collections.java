package one.tranic.t.utils;

import one.tranic.t.thread.Sys;
import org.intellij.lang.annotations.Flow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The Collections class provides utility methods for creating optimized data structures
 * such as maps, sets, and lists.
 * <p>
 * It dynamically determines whether to use the
 * optimized implementations from the FastUtil library or standard Java collections
 * based on the availability of FastUtil classes.
 */
@SuppressWarnings("unused")
public class Collections {
    private static final boolean fastutil = Sys.hasClass("it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap");

    /**
     * Creates a new hash map that maps keys of type {@code K} to integer values.
     *
     * @param <K> the type of keys maintained by this map
     * @return a new map that maps keys of type {@code K} to integers
     */
    public static <K> Map<K, Integer> newIntHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new hash map where keys are of type K and values are of type Integer.
     *
     * @param initialCapacity the initial capacity of the hash map. Must be a non-negative integer.
     * @return a new map instance with keys of type K and values of type Integer.
     */
    public static <K> Map<K, Integer> newIntHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * Creates and returns a new map instance where the keys are of generic type K,
     * and the values are of type Long.
     *
     * @param <K> the type of keys maintained by the map
     * @return a newly created map with generic key type K and Long values
     */
    public static <K> Map<K, Long> newLongHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new hash map with keys of type {@code K} and values of type {@code Long}.
     *
     * @param <K>             the type of keys to be used in the map
     * @param initialCapacity the initial capacity of the hash map; must be greater than or equal to 0
     * @return a new hash map instance with the specified initial capacity, either using a specialized
     * implementation or a standard {@code HashMap}
     */
    public static <K> Map<K, Long> newLongHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * Creates a new map with keys of type {@code K} and values of type {@link Float}.
     *
     * @param <K> the type of keys in the map
     * @return a new map capable of storing keys of type {@code K} and values of type {@link Float}
     */
    public static <K> Map<K, Float> newFloatHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new map with keys of generic type {@code K} and {@code float} values.
     *
     * @param <K>             the type of keys maintained by the map
     * @param initialCapacity the initial capacity of the map; must be non-negative
     * @return a new map instance with the respective initial capacity
     */
    public static <K> Map<K, Float> newFloatHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * Creates a new map with generic keys and double values.
     *
     * @param <K> the type of keys maintained by this map
     * @return a new map with keys of type K and values of type Double
     */
    public static <K> Map<K, Double> newDoubleHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new hash map with keys of type {@code K} and values of type {@code Double}.
     *
     * @param initialCapacity the initial capacity of the map. Must be a non-negative integer.
     * @return a new map of type {@code Map<K, Double>} with the specified initial capacity.
     */
    public static <K> Map<K, Double> newDoubleHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * Creates and returns a new Map instance where the values are of type Boolean.
     *
     * @param <K> the type of the keys in the map
     * @return a new Map instance with Boolean values
     */
    public static <K> Map<K, Boolean> newBooleanHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new {@link Map} instance with keys of type {@code K} and boolean values.
     *
     * @param initialCapacity the initial capacity of the map; must be a non-negative integer.
     * @return a new {@link Map} instance with the specified initial capacity.
     */
    public static <K> Map<K, Boolean> newBooleanHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * Creates and returns a new hash map with default settings.
     *
     * @param <K> the type of keys maintained by this map
     * @param <V> the type of mapped values
     * @return a new instance of a hash map
     */
    public static <K, V> Map<K, V> newHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new hash map with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the hash map; must be a non-negative integer
     * @return a new instance of a hash map with the given initial capacity
     */
    public static <K, V> Map<K, V> newHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * Creates a new HashMap instance and populates it with the entries from the provided map.
     *
     * @param map the map whose entries are to be added to the newly created map; must not be null
     * @return a new map containing all entries from the provided map
     */
    public static <K, V> Map<K, V> newHashMap(@NotNull Map<K, V> map) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<>(map) : new HashMap<>(map);
    }

    /**
     * Creates and returns a new instance of a LinkedHashMap.
     *
     * @param <K> the type of keys maintained by the map
     * @param <V> the type of values stored in the map
     * @return a new instance of a map, either LinkedHashMap or Object2ObjectLinkedOpenHashMap
     */
    public static <K, V> Map<K, V> newLinkedHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap<>() : new LinkedHashMap<>();
    }

    /**
     * Creates a new LinkedHashMap or a fastutil Object2ObjectLinkedOpenHashMap based on the specified map.
     *
     * @param map the input map whose entries are to be copied to the newly created map; must not be null
     * @return a new map containing the entries from the given map, maintaining insertion order
     */
    public static <K, V> Map<K, V> newLinkedHashMap(@NotNull Map<K, V> map) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap<>(map) : new LinkedHashMap<>(map);
    }

    /**
     * Creates a new LinkedHashMap with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the map, must be non-negative and within the valid range.
     * @return a new instance of a map implementing LinkedHashMap with the specified initial capacity.
     */
    public static <K, V> Map<K, V> newLinkedHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap<>(initialCapacity) : new LinkedHashMap<>(initialCapacity);
    }

    /**
     * Creates a new empty HashSet instance.
     *
     * @param <T> the type of elements maintained by the set.
     * @return a new empty {@code Set} instance.
     */
    public static <T> Set<T> newHashSet() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.ObjectOpenHashSet<>() : new HashSet<>();
    }

    /**
     * Creates a new unmodifiable empty set.
     *
     * @param <T> the type of elements that the set can hold
     * @return a new unmodifiable empty set
     */
    public static <T> Set<T> newUnmodifiableHashSet() {
        return fastutil ? it.unimi.dsi.fastutil.objects.ObjectSet.of() : java.util.Collections.unmodifiableSet(new HashSet<>());
    }

    /**
     * Creates a new unmodifiable hash set containing the elements of the provided set.
     *
     * @param set the input set whose elements will be included in the new unmodifiable set; must not be null
     * @return an unmodifiable set containing the elements of the input set
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> newUnmodifiableHashSet(@NotNull Set<T> set) {
        return fastutil ? it.unimi.dsi.fastutil.objects.ObjectSet.of((T[]) set.toArray()) : java.util.Collections.unmodifiableSet(set);
    }

    /**
     * Creates a new unmodifiable {@link Set} that contains the specified elements.
     *
     * @param elements the elements to be included in the set; cannot be null.
     * @return an unmodifiable set containing the specified elements.
     */
    @SafeVarargs
    public static <T> Set<T> newUnmodifiableHashSet(@NotNull @Flow(sourceIsContainer = true, targetIsContainer = true) T... elements) {
        if (fastutil) {
            return it.unimi.dsi.fastutil.objects.ObjectSet.of(elements);
        }
        List<T> list = new ArrayList<>(elements.length);
        java.util.Collections.addAll(list, elements);
        return Set.copyOf(list);
    }

    /**
     * Creates a new hash set with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the hash set; must be non-negative
     * @return a newly created hash set instance with the specified initial capacity
     */
    public static <T> Set<T> newHashSet(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.ObjectOpenHashSet<>(initialCapacity) : new HashSet<>(initialCapacity);
    }

    /**
     * Creates a new TreeSet with the elements provided in the specified collection.
     * Depending on the context, either a {@code TreeSet} or a {@code ObjectAVLTreeSet}
     * from the fastutil library will be created and returned.
     *
     * @param <T> the type of elements maintained by the set
     * @param c   the collection whose elements are to be placed into the new set
     * @return a newly created TreeSet containing the elements from the provided collection
     */
    public static <T> Set<T> newTreeSet(@NotNull Collection<? extends T> c) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.ObjectAVLTreeSet<>(c) : new TreeSet<>(c);
    }

    /**
     * Creates a new TreeSet instance with the elements provided in the specified collection.
     * Depending on the configuration, it uses either a fastutil ObjectRBTreeSet or a standard TreeSet.
     *
     * @param <T> the type of elements maintained by the set
     * @param c   the collection whose elements are to be placed into the new set; must not be null
     * @return a newly created TreeSet containing all elements of the specified collection
     */
    public static <T> Set<T> newTreeSetRB(@NotNull @Flow(sourceIsContainer = true, targetIsContainer = true) Collection<? extends T> c) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.ObjectRBTreeSet<>(c) : new TreeSet<>(c);
    }

    /**
     * Creates a new empty list instance.
     * The implementation type of the list returned depends on the runtime configuration.
     *
     * @param <T> The type of elements that the list will hold.
     * @return A new instance of an empty {@link List}.
     */
    public static <T> List<T> newArrayList() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.ObjectArrayList<>() : new ArrayList<>();
    }

    /**
     * Creates a new unmodifiable list.
     *
     * @param <T> the type of elements in the list
     * @return a new unmodifiable list instance
     */
    public static <T> List<T> newUnmodifiableList() {
        return fastutil ? it.unimi.dsi.fastutil.objects.ObjectList.of() : java.util.Collections.unmodifiableList(new ArrayList<>());
    }

    /**
     * Creates a new unmodifiable list from the provided list.
     *
     * @param list the list from which the unmodifiable list is to be created; must not be null
     * @return an unmodifiable list containing the same elements as the provided list
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> newUnmodifiableList(@NotNull List<T> list) {
        return fastutil
                ? it.unimi.dsi.fastutil.objects.ObjectList.of((T[]) list.toArray())
                : java.util.Collections.unmodifiableList(list);
    }

    /**
     * Creates a new unmodifiable list containing the specified elements.
     *
     * @param <T>      the type of elements in the list
     * @param elements the elements to include in the unmodifiable list; must not be null
     * @return an unmodifiable list containing the provided elements
     */
    @SafeVarargs
    public static <T> List<T> newUnmodifiableList(@NotNull T... elements) {
        if (fastutil) {
            return it.unimi.dsi.fastutil.objects.ObjectList.of(elements);
        }
        List<T> list = new ArrayList<>(elements.length);
        java.util.Collections.addAll(list, elements);
        return java.util.Collections.unmodifiableList(list);
    }

    /**
     * Creates and returns a new instance of a list that stores integers.
     *
     * @return a new list instance for storing integers, either an IntArrayList
     * or an ArrayList based on the 'fastutil' flag.
     */
    public static List<Integer> newIntArrayList() {
        return fastutil ? new it.unimi.dsi.fastutil.ints.IntArrayList() : new ArrayList<>();
    }

    /**
     * Creates a new list of integers initialized with the given values.
     *
     * @param initialValues the integer values to initialize the list with
     * @return a new list of integers containing the provided initial values
     */
    public static List<Integer> newIntArrayList(int... initialValues) {
        if (fastutil) return new it.unimi.dsi.fastutil.ints.IntArrayList(initialValues);
        List<Integer> list = new ArrayList<>(initialValues.length);
        for (int initialValue : initialValues) list.add(initialValue);
        return list;
    }

    /**
     * Creates a new list of integers with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list, must be non-negative
     * @return a new instance of a list of integers with the specified initial capacity
     */
    public static List<Integer> newIntArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.ints.IntArrayList(initialCapacity) : new ArrayList<>(initialCapacity);
    }

    /**
     * Creates a new list of Long type.
     *
     * @return a new list of Long elements, using a specific implementation
     * based on the configuration.
     */
    public static List<Long> newLongArrayList() {
        return fastutil ? new it.unimi.dsi.fastutil.longs.LongArrayList() : new ArrayList<>();
    }

    /**
     * Creates a new List of Longs initialized with the given long values.
     *
     * @param initialValues an optional list of long values to initialize the list with
     * @return a new List of Longs containing the given initial values
     */
    public static List<Long> newLongArrayList(long... initialValues) {
        if (fastutil) return new it.unimi.dsi.fastutil.longs.LongArrayList(initialValues);
        List<Long> list = new ArrayList<>(initialValues.length);
        for (long initialValue : initialValues) list.add(initialValue);
        return list;
    }

    /**
     * Creates a new instance of a list that can hold Long objects with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list. Must be a non-negative integer value.
     * @return a List of Long objects with the specified initial capacity.
     */
    public static List<Long> newLongArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.longs.LongArrayList(initialCapacity) : new ArrayList<>(initialCapacity);
    }

    /**
     * Creates a new list of Float type.
     *
     * @return a new list of Float type, using the specified implementation
     * based on the 'fastutil' flag.
     */
    public static List<Float> newFloatArrayList() {
        return fastutil ? new it.unimi.dsi.fastutil.floats.FloatArrayList() : new ArrayList<>();
    }

    /**
     * Creates a new list of Floats containing the provided initial values.
     *
     * @param initialValues an array of float values to initialize the list with
     * @return a List of Float containing the provided initial values
     */
    public static List<Float> newFloatArrayList(float... initialValues) {
        if (fastutil) return new it.unimi.dsi.fastutil.floats.FloatArrayList(initialValues);
        List<Float> list = new ArrayList<>(initialValues.length);
        for (float initialValue : initialValues) list.add(initialValue);
        return list;
    }

    /**
     * Creates a new instance of a list to hold float values with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list. Must be a non-negative integer.
     * @return a list capable of storing float values, utilizing either a FastUtil implementation
     * or a standard ArrayList based on the configuration.
     */
    public static List<Float> newFloatArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.floats.FloatArrayList(initialCapacity) : new ArrayList<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of a list that stores Double values.
     *
     * @return a new list for storing Double values, either a FastUtil `DoubleArrayList` or a Java `ArrayList`
     */
    public static List<Double> newDoubleArrayList() {
        return fastutil ? new it.unimi.dsi.fastutil.doubles.DoubleArrayList() : new ArrayList<>();
    }

    /**
     * Creates a new list of Doubles initialized with the given double values.
     *
     * @param initialValues an array of double values to initialize the list
     * @return a new list containing the given double values
     */
    public static List<Double> newDoubleArrayList(double... initialValues) {
        if (fastutil) return new it.unimi.dsi.fastutil.doubles.DoubleArrayList(initialValues);
        List<Double> list = new ArrayList<>(initialValues.length);
        for (double initialValue : initialValues) list.add(initialValue);
        return list;
    }

    /**
     * Creates a new list capable of storing double values with the specified initial capacity.
     *
     * @param initialCapacity the initial size of the list, must be a non-negative integer.
     * @return a new instance of a list that can contain double values, using FastUtil's implementation
     * if available, otherwise a standard ArrayList.
     */
    public static List<Double> newDoubleArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.doubles.DoubleArrayList(initialCapacity) : new ArrayList<>(initialCapacity);
    }

    /**
     * Creates and returns a new list to store Boolean values.
     *
     * @return a new list instance for storing Boolean values, either a FastUtil
     * BooleanArrayList or a standard ArrayList, depending on the fastutil flag.
     */
    public static List<Boolean> newBooleanArrayList() {
        return fastutil ? new it.unimi.dsi.fastutil.booleans.BooleanArrayList() : new ArrayList<>();
    }

    /**
     * Creates a new list of Boolean objects initialized with the provided boolean values.
     *
     * @param initialValues the initial boolean values to populate the list with
     * @return a list of Boolean objects containing the specified initial values
     */
    public static List<Boolean> newBooleanArrayList(boolean... initialValues) {
        if (fastutil) return new it.unimi.dsi.fastutil.booleans.BooleanArrayList(initialValues);
        List<Boolean> list = new ArrayList<>(initialValues.length);
        for (boolean initialValue : initialValues) list.add(initialValue);
        return list;
    }

    /**
     * Creates a new list of Booleans with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list; must be non-negative.
     * @return a new instance of a list of Booleans with the specified initial capacity.
     */
    public static List<Boolean> newBooleanArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.booleans.BooleanArrayList(initialCapacity) : new ArrayList<>(initialCapacity);
    }

    /**
     * Creates and returns a new list of Byte elements.
     */
    public static List<Byte> newByteArrayList() {
        return fastutil ? new it.unimi.dsi.fastutil.bytes.ByteArrayList() : new ArrayList<>();
    }

    /**
     * Creates a new list of Byte objects initialized with the specified byte values.
     *
     * @param initialValues the initial byte values to populate the list
     * @return a new List of Byte objects containing the specified values
     */
    public static List<Byte> newByteArrayList(byte... initialValues) {
        if (fastutil) return new it.unimi.dsi.fastutil.bytes.ByteArrayList(initialValues);
        List<Byte> list = new ArrayList<>(initialValues.length);
        for (byte initialValue : initialValues) list.add(initialValue);
        return list;
    }

    /**
     * Creates a new list to hold Byte elements with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list, must be between 0 and Integer.MAX_VALUE inclusive
     * @return a new list capable of holding Byte elements with the specified initial capacity
     */
    public static List<Byte> newByteArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.bytes.ByteArrayList(initialCapacity) : new ArrayList<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of a list that can store Short objects.
     *
     * @return A new list for storing Short objects, either a fastutil ShortArrayList or a standard ArrayList.
     */
    public static List<Short> newShortArrayList() {
        return fastutil ? new it.unimi.dsi.fastutil.shorts.ShortArrayList() : new ArrayList<>();
    }

    /**
     * Creates a new List of Short values, optionally initializing it with the provided short values.
     *
     * @param initialValues an optional array of short values to initialize the list with
     * @return a List containing the initial short values, or an empty list if no values are provided
     */
    public static List<Short> newShortArrayList(short... initialValues) {
        if (fastutil) return new it.unimi.dsi.fastutil.shorts.ShortArrayList(initialValues);
        List<Short> list = new ArrayList<>(initialValues.length);
        for (short initialValue : initialValues) list.add(initialValue);
        return list;
    }

    /**
     * Creates a new list of Short objects with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list; must be a non-negative value.
     * @return a new List of Short objects with the specified initial capacity.
     */
    public static List<Short> newShortArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.shorts.ShortArrayList(initialCapacity) : new ArrayList<>(initialCapacity);
    }

    /**
     * Creates and returns a new list of characters.
     *
     * @return a new list of characters, either a `CharArrayList` or an `ArrayList` depending on the `fastutil` flag.
     */
    public static List<Character> newCharArrayList() {
        return fastutil ? new it.unimi.dsi.fastutil.chars.CharArrayList() : new ArrayList<>();
    }

    /**
     * Creates a new {@code List} of {@code Character} elements from the given array of primitive {@code char} values.
     *
     * @param initialValues an array of primitive {@code char} values to initialize the list with
     * @return a new {@code List} containing the provided {@code char} values as {@code Character} objects
     */
    public static List<Character> newCharArrayList(char... initialValues) {
        if (fastutil) return new it.unimi.dsi.fastutil.chars.CharArrayList(initialValues);
        List<Character> list = new ArrayList<>(initialValues.length);
        for (char initialValue : initialValues) list.add(initialValue);
        return list;
    }

    /**
     * Creates and returns a new list of Characters with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list, must be a non-negative integer.
     * @return a new list of Characters with the specified initial capacity.
     */
    public static List<Character> newCharArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.chars.CharArrayList(initialCapacity) : new ArrayList<>(initialCapacity);
    }

    /**
     * Creates a new {@link List} instance with the specified initial size.
     *
     * @param <T>  The type of elements the list will contain.
     * @param size The initial size of the list. Must be greater than or equal to 0.
     * @return A new list instance with the specified size.
     */
    public static <T> List<T> newArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int size) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.ObjectArrayList<>(size) : new ArrayList<>(size);
    }

    /**
     * Creates a new array-backed {@link List} containing the provided elements.
     *
     * @param <T>      the type of elements in the list
     * @param elements the elements to include in the new list; must not be null
     * @return a new {@link List} containing the specified elements
     */
    @SafeVarargs
    public static <T> List<T> newArrayList(@NotNull T... elements) {
        if (fastutil) {
            return new it.unimi.dsi.fastutil.objects.ObjectArrayList<>(elements);
        }
        List<T> list = new ArrayList<>(elements.length);
        java.util.Collections.addAll(list, elements);
        return list;
    }

    /**
     * Creates a new list containing the elements from the specified collection.
     *
     * @param elements the collection whose elements are to be placed into the new list, must not be null
     * @return a new list containing the elements from the specified collection
     */
    public static <T> List<T> newArrayList(@NotNull Collection<? extends T> elements) {
        return fastutil
                ? new it.unimi.dsi.fastutil.objects.ObjectArrayList<>(elements)
                : new ArrayList<>(elements);
    }

    /**
     * Creates a new {@code ArrayList} containing the elements from the provided {@code Iterable}.
     * If the {@code Iterable} is a {@code Collection}, it leverages its properties to create the list more efficiently.
     *
     * @param <T>      the type of elements in the list
     * @param elements the {@code Iterable} whose elements are to be placed into the new list; must not be null
     * @return a new {@code ArrayList} containing the elements from the provided {@code Iterable}
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> newArrayList(@NotNull Iterable<? extends T> elements) {
        return elements instanceof Collection ? newArrayList((Collection<T>) elements) : newArrayList(elements.iterator());
    }

    /**
     * Creates a new {@code List} containing all elements from the given {@code Iterator}.
     *
     * @param <T>      the type of elements in the list
     * @param elements an {@code Iterator} containing elements to be added to the list
     * @return a new {@code List} containing all the elements from the given {@code Iterator}
     */
    public static <T> List<T> newArrayList(@NotNull Iterator<? extends T> elements) {
        List<T> list = newArrayList();
        while (elements.hasNext()) list.add(elements.next());
        return list;
    }


    /**
     * Iterates over each entry in the provided map and applies the given consumer action to each entry.
     *
     * @param <K>      the type of keys maintained by the map
     * @param <V>      the type of mapped values
     * @param map      the map whose entries are to be processed
     * @param consumer the action to be performed for each map entry
     */
    public static <K, V> void entryForEach(@NotNull Map<K, V> map, @NotNull final Consumer<? super Map.Entry<K, V>> consumer) {
        if (fastutil && map instanceof it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<K, V> fastMap) {
            fastMap.object2ObjectEntrySet().fastForEach(consumer);
        } else map.entrySet().forEach(consumer);
    }

    /**
     * Removes all entries from the specified map that satisfy the provided predicate.
     *
     * @param map    the map from which entries are to be removed based on the given predicate
     * @param filter the predicate that tests each entry; entries that satisfy this predicate are removed
     * @return {@code true} if any entries were removed from the map, otherwise {@code false}
     */
    public static <K, V> boolean removeIf(@NotNull Map<K, V> map, @NotNull Predicate<? super Map.Entry<K, V>> filter) {
        return (fastutil && map instanceof it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<K, V> fastMap) ?
                fastMap.object2ObjectEntrySet().removeIf(filter) :
                map.entrySet().removeIf(filter);
    }
}
