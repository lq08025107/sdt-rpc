package com.sdt.rpc.registry.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class JedisAPI {
    private static Jedis jedis;
    public static void jedisSet(){
        jedis = new Jedis("localhost");
        long start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++) {
            String result = jedis.set("n"+ i, "n" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Simple SET: " + ((end - start) / 1000.0) + " seconds");
        jedis.disconnect();
    }
    public static void jedisPipelined() {
        jedis = new Jedis("localhost");
        Pipeline pipeline = jedis.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("p" + i, "p" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined SET: " + ((end - start) / 1000.0) + " seconds");
        jedis.disconnect();
    }
    public static void main(String[] args){
        JedisAPI.jedisSet();
        JedisAPI.jedisPipelined();
    }
}
