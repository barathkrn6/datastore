package com.barath.datastore.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controller {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @GetMapping("/health_check")
    public String healthCheck() {
        IMap<Object, Object> map = hazelcastInstance.getMap("test");
        map.put("test", "health-check");
        return map.get("test").toString();
    }
}
