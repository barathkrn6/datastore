package com.barath.datastore.service.impl;

import com.barath.datastore.service.ClusterMap;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HazelcastMap<K, V> implements ClusterMap<K, V> {
    IMap<K, V> hzMap;

    public HazelcastMap(final IMap<K, V> hzMap) {
        this.hzMap = hzMap;
    }

    /**
     * get the valused based on the key
     *
     * @param key
     * @return
     */
    @Override
    public Optional<V> get(final K key) {
        return Optional.ofNullable(hzMap.get(key));
    }

    /**
     * put key and value to hazelcast map
     *
     * @param key
     * @param value
     */
    @Override
    public void put(final K key, final V value) {
        hzMap.put(key, value);
    }

    /**
     * set time to leave (expiry) for the given key
     *
     * @param key
     * @param seconds
     */
    @Override
    public void setTtl(final K key, final Integer seconds) {
        hzMap.setTtl(key, seconds, TimeUnit.SECONDS);
    }
}
