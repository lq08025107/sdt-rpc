package com.sdt.rpc.registry.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Publisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Publisher.class);

    private final Jedis jedis;

    private final String channel;

    public Publisher(Jedis jedis, String channel){
        this.jedis = jedis;
        this.channel = channel;
    }

    public void startPublish(){
        LOGGER.info("type your message(quit for terminate)");
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                String line = reader.readLine();
                if(!"quit".equals(line)){
                    jedis.publish(channel, line);
                } else {
                    break;
                }
            }
        } catch (IOException e){
            LOGGER.error("IO failure");
        }
    }
}
