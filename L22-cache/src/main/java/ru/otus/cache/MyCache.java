package ru.otus.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.WeakHashMap;


public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(HwCache.class);
    private final WeakHashMap cache = new WeakHashMap<>();
    private final ArrayList<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        notifyListeners(key, value, "put");
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        notifyListeners(key, (V) cache.get(key), "remove");
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        V value = (V) cache.get(key);
        notifyListeners(key, value, "get");
        return (value);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add((HwListener<K, V>) listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    public int size() {
        return cache.size();
    }

    private void notifyListeners(K key, V value, String action) {
        try {
            listeners.forEach(listener -> listener.notify(key, value, action));
        } catch (Exception exc) {
            log.error("error notifyListeners :{}", exc.getMessage());
        }
    }
}
