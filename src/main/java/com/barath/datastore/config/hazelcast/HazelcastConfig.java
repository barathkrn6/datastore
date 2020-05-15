package com.barath.datastore.config.hazelcast;

import com.google.common.base.MoreObjects;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import com.hazelcast.config.*;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Create by Barath
 */
@Slf4j
@Configuration
public class HazelcastConfig implements HZQuorumListener {

    @Value("${clustering.hazelcast.quorum}")
    int quorum;
    @Value("${hazelcast.all.address}")
    String hazelcastAllAddresses;
    @Value("${hazelcast.all.count.nodes}")
    int hazelcastNodeCount;
    AtomicBoolean isQuorum;
    @Value("${hazelcast.address}")
    private String hazelCastAddress;

    @Bean
    HazelcastInstance createClientInstance() {
        ClientConfig config = new ClientConfig();
        config.setClusterName("dev");
        config.getNetworkConfig().addAddress(hazelCastAddress.split(","));
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(config);
        isQuorum = calculateIsQuorum(hazelcastInstance);
        log.info("isQuorum :: {}", isQuorum);
        hazelcastInstance.getCluster().addMembershipListener(new MembershipListener() {
            @Override
            public void memberAdded(MembershipEvent membershipEvent) {
                log.info("Member Added :: {}", membershipEvent);
                isQuorum.set(membershipEvent.getMembers().size() >= getexpectedQuorum());
            }

            @Override
            public void memberRemoved(MembershipEvent membershipEvent) {
                log.info("Member Removed :: {}", membershipEvent);
                isQuorum.set(membershipEvent.getMembers().size() >= getexpectedQuorum());
                if (!isQuorum.get()) {
                    log.error("Alertssssssssss :: {}", membershipEvent.getMember().getAddress().getHost());
                }
            }
        });
        return hazelcastInstance;
    }

    private AtomicBoolean calculateIsQuorum(HazelcastInstance hazelcastInstance) {
        int clusterSize = hazelcastInstance.getCluster().getMembers().size();
        double expectedQuorum = getexpectedQuorum();
        return new AtomicBoolean(clusterSize >= expectedQuorum);
    }

    private double getexpectedQuorum() {
        return Math.ceil((hazelcastNodeCount + 1) / 2);
    }

    private void addNodeToCluster(String member) throws Exception {
        Config config = new Config();
        NetworkConfig network = config.getNetworkConfig();
        JoinConfig join = network.getJoin();
        MulticastConfig multicast = join.getMulticastConfig();
        multicast.setEnabled(false);
        TcpIpConfig tcpIp = join.getTcpIpConfig();
        tcpIp.setEnabled(true);
        InetAddress[] addresses = MoreObjects.firstNonNull(InetAddress.getAllByName(member), new InetAddress[0]);
        for (final InetAddress addr : addresses) {
            final String hostAddress = addr.getHostAddress();
            tcpIp.addMember(hostAddress);
            log.info("[Hazelcast] New Member: " + hostAddress);
        }
    }

    @Override
    public boolean isQuorum() {
        return isQuorum.get();
    }

}
