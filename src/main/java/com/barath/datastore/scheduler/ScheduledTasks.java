package com.barath.datastore.scheduler;

import com.barath.datastore.config.hazelcast.HazelcastConfig;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class ScheduledTasks {
    @Autowired
    private HazelcastConfig hazelcastConfig;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    private static int retryCount = 0;

    @Scheduled(fixedRate = 2000)
    public void reportCurrentTime() {

        if (!hazelcastConfig.isQuorum()) {
            ++retryCount;
            log.error("Minimum required hazelcast nodes are not available, retrying after 2000 ms. Retry count - {}. " +
                            "PLEASE BRING THE REQUIRED NODES UP", retryCount);
        } else {
            retryCount = 0;
        }
    }
}
