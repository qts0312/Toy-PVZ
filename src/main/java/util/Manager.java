package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Manager class
 * <p>
 *      Manage a set of objects. In this situation, we use it to manage a set of
 *      plants, zombies, bullets and so on.
 * </p>
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class Manager<K, V> {
    private final HashMap<K, V> map = new HashMap<>();
    private final boolean unique;// Can members be duplicated?

    /**
     * Key class
     * <p>
     *      This class is used as the key of the hashmap in Manager class.
     *      It is normally used in our project.
     * </p>
     */
    public static class Key {
        private static final int ADD = 36000;

        private final int key;

        private final int add;

        public Key(int key, int add) {
            this.key = key;
            this.add = add;
        }

        public Key(int key) {
            this(key, 0);
        }

        @Override
        public int hashCode() {
            long code = key + (long) add * ADD;
            if (code > Integer.MAX_VALUE) {
                code %= Integer.MAX_VALUE;
            }
            return (int) code;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key) {
                Key key = (Key) obj;
                return this.key == key.key &&
                        this.add == key.add;
            }
            return false;
        }

        @Override
        public String toString() {
            return "(main key=" + key + ", add key=" + add + ")";
        }
    }

    public Manager(boolean unique) {
        this.unique = unique;
    }

    public boolean add(K key, V value) {
        if (unique && map.containsKey(key)) {
            return false;
        }
        map.put(key, value);
        return true;
    }

    public void remove(K key) {
        map.remove(key);
    }

    public V get(Function<V, Boolean> filter) {
        for (V value : map.values()) {
            if (filter.apply(value)) {
                return value;
            }
        }
        return null;
    }

    public ArrayList<V> getAll() {
        return new ArrayList<>(map.values());
    }
}
