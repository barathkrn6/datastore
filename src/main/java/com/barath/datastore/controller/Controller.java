package com.barath.datastore.controller;

import com.barath.datastore.service.ClusterMap;
import com.barath.datastore.service.ClusterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@EnableScheduling
public class Controller {

    @Autowired
    private ClusterService clusterService;

    @Value("${hazelcast.map.name}")
    private String hzMapName;

    /**
     * Healthcheck to check if application is up and also able to connect to distributed system
     *
     * @return
     */
    @GetMapping("/health_check")
    public Optional<String> healthCheck() {
        return clusterService.healthCheck();
    }

    /**
     * get the valused based on the key
     *
     * @param key
     * @return
     */
    @GetMapping("/get/{key}")
    Optional<String> get(@PathVariable("key") final String key) {
        final ClusterMap<String, String> hzMap = clusterService.getMap(hzMapName);
        return hzMap.get(key);
    }

    /**
     * put key and value to hazelcast map
     *
     * @param key
     * @param value
     */
    @PutMapping("/put/{key}/{value}")
    void get(@PathVariable("key") final String key, @PathVariable("value") final String value) {
        final ClusterMap<String, String> map = clusterService.getMap(hzMapName);
        map.put(key, value);
    }

    /**
     * set time to leave (expiry) for the given key
     *
     * @param key
     * @param value
     */
    @PutMapping("/set_ttl/{key}/{value}")
    void setTtl(@PathVariable("key") final String key, @PathVariable("value") final Integer value) {
        final ClusterMap<String, String> map = clusterService.getMap(hzMapName);
        map.setTtl(key, value);
    }
}
