package com.barath.datastore.config.hazelcast;

import org.springframework.context.annotation.Configuration;

@FunctionalInterface
@Configuration
public interface HZQuorumListener {
    boolean isQuorum();
}
