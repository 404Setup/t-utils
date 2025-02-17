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
public class Collections {
    private static final boolean fastutil = Sys.hasClass("it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap");

    /**
     * Creates a new hash map that maps keys of type {@code K} to integer values.
     * <p>
     * Depending on the configuration, the implementation may use either
     * {@code Object2IntOpenHashMap} from fastutil or {@code HashMap} from Java's standard library.
     *
     * @param <K> the type of keys maintained by this map
     * @return a new map that maps keys of type {@code K} to integers
     */
    public static <K> Map<K, Integer> newIntHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new hash map where keys are of type K and values are of type Integer.
     * <p>
     * Depending on the underlying configuration, this method may create either a
     * HashMap instance or a fastutil Object2IntOpenHashMap instance.
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
     * <p>
     * The specific implementation of the map depends on the runtime evaluation of the `fastutil` flag.
     * <p>
     * If `fastutil` is true, the map
     * will be an instance of `it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap`.
     * <p>
     * Otherwise, it will fall back to a standard Java `HashMap`.
     *
     * @param <K> the type of keys maintained by the map
     * @return a newly created map with generic key type K and Long values
     */
    public static <K> Map<K, Long> newLongHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new hash map with keys of type {@code K} and values of type {@code Long}.
     * <p>
     * Optionally utilizes a specialized implementation for performance optimization
     * if the {@code fastutil} flag is enabled.
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
     * <p>
     * This method returns an instance of either {@link it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap}
     * or {@link HashMap}, based on the runtime environment configuration. It is designed to
     * optimize performance when handling float values as map entries.
     *
     * @param <K> the type of keys in the map
     * @return a new map capable of storing keys of type {@code K} and values of type {@link Float}
     */
    public static <K> Map<K, Float> newFloatHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new map with keys of generic type {@code K} and {@code float} values.
     * <p>
     * The initial capacity of the map is specified by the provided parameter.
     * <p>
     * This method switches between using a FastUtil implementation or a standard Java HashMap
     * depending on the underlying configuration.
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
     * <p>
     * If the `fastutil` flag is true, an instance of `Object2DoubleOpenHashMap` from the FastUtil library
     * is returned. Otherwise, a standard Java `HashMap` is created.
     *
     * @param <K> the type of keys maintained by this map
     * @return a new map with keys of type K and values of type Double
     */
    public static <K> Map<K, Double> newDoubleHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new hash map with keys of type {@code K} and values of type {@code Double}.
     * <p>
     * The map's initial capacity is specified by the parameter.
     * <p>
     * Depending on the internal configuration, it either creates an instance of {@code Object2DoubleOpenHashMap}
     * from the FastUtil library or a standard {@code HashMap}.
     *
     * @param initialCapacity the initial capacity of the map. Must be a non-negative integer.
     * @return a new map of type {@code Map<K, Double>} with the specified initial capacity.
     */
    public static <K> Map<K, Double> newDoubleHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * Creates and returns a new Map instance where the values are of type Boolean.
     * <p>
     * The type of Map implementation used depends on the value of the `fastutil` field:
     * <p>
     * - If `fastutil` is true, an instance of `it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap` is returned.
     * <p>
     * - If `fastutil` is false, a standard `HashMap` is returned.
     *
     * @param <K> the type of the keys in the map
     * @return a new Map instance with Boolean values
     */
    public static <K> Map<K, Boolean> newBooleanHashMap() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap<>() : new HashMap<>();
    }

    /**
     * Creates a new {@link Map} instance with keys of type {@code K} and boolean values.
     * <p>
     * Allows specifying the initial capacity for the map to optimize performance.
     * <p>
     * If the `fastutil` flag is enabled, it uses a `Object2BooleanOpenHashMap` from the fastutil library;
     * otherwise, it defaults to using a standard {@link HashMap}.
     *
     * @param initialCapacity the initial capacity of the map; must be a non-negative integer.
     * @return a new {@link Map} instance with the specified initial capacity.
     */
    public static <K> Map<K, Boolean> newBooleanHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * Creates and returns a new hash map with default settings.
     * <p>
     * The implementation of the hash map may vary based on internal configuration.
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
     * <p>
     * Depending on the configuration, it will use either a standard HashMap or a
     * fastutil-specific Object2ObjectOpenHashMap implementation.
     *
     * @param map the map whose entries are to be added to the newly created map; must not be null
     * @return a new map containing all entries from the provided map
     */
    public static <K, V> Map<K, V> newHashMap(@NotNull Map<K, V> map) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<>(map) : new HashMap<>(map);
    }

    /**
     * Creates and returns a new instance of a LinkedHashMap or a fastutil-based
     * Object2ObjectLinkedOpenHashMap based on the value of the 'fastutil' flag.
     * <p>
     * If 'fastutil' is true, an Object2ObjectLinkedOpenHashMap is returned;
     * otherwise, a standard LinkedHashMap is created.
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
     * <p>
     * Uses a fastutil implementation if available, otherwise defaults to Java's LinkedHashMap.
     *
     * @param initialCapacity the initial capacity of the map, must be non-negative and within the valid range.
     * @return a new instance of a map implementing LinkedHashMap with the specified initial capacity.
     */
    public static <K, V> Map<K, V> newLinkedHashMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        return fastutil ? new it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap<>(initialCapacity) : new LinkedHashMap<>(initialCapacity);
    }

    /**
     * Creates a new empty HashSet instance.
     * <p>
     * Depending on the configuration, this method returns either an instance
     * of {@code it.unimi.dsi.fastutil.objects.ObjectOpenHashSet} if the fastutil
     * optimization is enabled, or a standard {@code java.util.HashSet} otherwise.
     *
     * @param <T> the type of elements maintained by the set.
     * @return a new empty {@code Set} instance.
     */
    public static <T> Set<T> newHashSet() {
        return fastutil ? new it.unimi.dsi.fastutil.objects.ObjectOpenHashSet<>() : new HashSet<>();
    }

    /**
     * Creates a new unmodifiable empty set.
     * The implementation of the set may vary depending on the internal configuration.
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
    public static <T> Set<T> newTreeSet(Collection<? extends T> c) {
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
     * Creates a new unmodifiable list. The implementation used for the unmodifiable list
     * depends on whether the `fastutil` flag is enabled. If `fastutil` is true, it uses
     * the `ObjectList.of()` from the FastUtil library. Otherwise, it uses
     * `Collections.unmodifiableList` wrapping an empty `ArrayList`.
     *
     * @param <T> the type of elements in the list
     * @return a new unmodifiable list instance
     */
    public static <T> List<T> newUnmodifiableList() {
        return fastutil ? it.unimi.dsi.fastutil.objects.ObjectList.of() : java.util.Collections.unmodifiableList(new ArrayList<>());
    }

    /**
     * Creates a new unmodifiable list from the provided list. The method ensures
     * that the returned list is immutable and changes to the original list will
     * not reflect in the new unmodifiable list.
     *
     * @param list the list from which the unmodifiable list is to be created; must not be null
     * @return an unmodifiable list containing the same elements as the provided list
     */
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
     * Creates a new {@link List} instance with the specified initial size.
     * Depending on the configuration, it uses either a fastutil-backed implementation
     * or a standard {@link ArrayList}.
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
     * The returned list may be an instance of a more specialized implementation
     * depending on the runtime configuration.
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
     * Iterates over each entry in the provided map and applies the given consumer action to each entry.
     * If the map is an instance of a fastutil Object2ObjectOpenHashMap, it uses optimized iteration,
     * otherwise defaults to standard map entry iteration.
     *
     * @param <K>      the type of keys maintained by the map
     * @param <V>      the type of mapped values
     * @param map      the map whose entries are to be processed
     * @param consumer the action to be performed for each map entry
     */
    public static <K, V> void entryForEach(Map<K, V> map, final Consumer<? super Map.Entry<K, V>> consumer) {
        if (fastutil && map instanceof it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<K, V> fastMap) {
            fastMap.object2ObjectEntrySet().fastForEach(consumer);
        } else map.entrySet().forEach(consumer);
    }

    /**
     * Removes all entries from the specified map that satisfy the provided predicate.
     * If the map is an instance of {@code Object2ObjectOpenHashMap} from FastUtil, it utilizes
     * the optimized removal method for that implementation; otherwise, it defaults to the standard Java map implementation.
     *
     * @param map    the map from which entries are to be removed based on the given predicate
     * @param filter the predicate that tests each entry; entries that satisfy this predicate are removed
     * @return {@code true} if any entries were removed from the map, otherwise {@code false}
     */
    public static <K, V> boolean removeIf(Map<K, V> map, Predicate<? super Map.Entry<K, V>> filter) {
        return (fastutil && map instanceof it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<K, V> fastMap) ?
                fastMap.object2ObjectEntrySet().removeIf(filter) :
                map.entrySet().removeIf(filter);
    }
}
