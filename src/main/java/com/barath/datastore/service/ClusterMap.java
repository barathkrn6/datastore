package com.barath.datastore.service;

import java.util.Optional;

public interface ClusterMap<K, V> {
    Optional<V> get(final K key);

    void put(final K key, final V value);

    void setTtl(final K key, final Integer seconds);
}
