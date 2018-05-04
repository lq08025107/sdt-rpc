package com.sdt.rpc.registry.redis;

import com.sdt.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class RedisServiceRegistry implements ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceRegistry.class);

    private Jedis jedis;

    public RedisServiceRegistry(String redisAddress){
        jedis = new Jedis(redisAddress);
    }
    @Override
    public void registry(String serviceName, String serviceAddress) {

    }
}
