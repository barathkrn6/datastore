package com.barath.datastore.service.impl;

import com.barath.datastore.service.ClusterMap;
import com.barath.datastore.service.ClusterService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@org.springframework.stereotype.Service
@Slf4j
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    /**
     * healthCkech Function
     *
     * @param <K>
     * @param <V>
     * @return
     */
    @Override
    public <K, V> Optional<String> healthCheck() {
        ClusterMap<String, String> hzMap = getMap("test");
        String key = "test";
        String value = "health-check";
        hzMap.put(key, value);
        return hzMap.get(key);
    }

    /**
     * Get Hazelcast map from cluster
     *
     * @param id
     * @param <K>
     * @param <V>
     * @return
     */
    @Override
    public <K, V> ClusterMap<K, V> getMap(String id) {
        final IMap<K, V> hzMap = hazelcastInstance.getMap(id);
        return new HazelcastMap<>(hzMap);
    }
}
