package ru.otus.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final WeakHashMap cache = new WeakHashMap<>();
    private final ArrayList<HwListener<String, Integer>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return (V) cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add((HwListener<String, Integer>) listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    public int size() {
        return cache.size();
    }
}
