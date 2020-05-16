package com.barath.datastore.service;

import java.util.Optional;

public interface ClusterService {
    <K, V> Optional<String> healthCheck();

    <K, V> ClusterMap<K, V> getMap(String id);
}
