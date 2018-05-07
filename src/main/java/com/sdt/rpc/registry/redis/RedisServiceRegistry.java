package com.sdt.rpc.registry.redis;

import com.sdt.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class RedisServiceRegistry implements ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceRegistry.class);
    private static final Integer EXPIRE = Integer.MAX_VALUE;
    private Jedis jedis;

    public RedisServiceRegistry(String redisAddress){
        jedis = new Jedis(redisAddress);
    }
    @Override
    public void registry(String serviceName, String serviceAddress) {

        Map<String, String> address = new HashMap<>();

        if(jedis.exists(serviceName)){
            //列表中增加值
            address.put(serviceAddress, EXPIRE.toString());
            jedis.hmset(serviceName, address);
            LOGGER.debug("add");
        } else {
            //创建
            address.put(serviceAddress, EXPIRE.toString());
            jedis.hmset(serviceName, address);
            LOGGER.debug("new");
        }
    }

    public static void main(String[] args){
        RedisServiceRegistry registry = new RedisServiceRegistry("127.0.0.1");
        registry.registry("com.sdt.rpc.person","127.0.0.1:8001");
    }
}
