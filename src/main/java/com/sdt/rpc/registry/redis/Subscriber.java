package com.sdt.rpc.registry.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {
    private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class);

    @Override
    public void onMessage(String channel, String message){
        LOGGER.info(String.format("Message. Channel: %s, Msg: %s", channel, message));
    }

    @Override
    public void onPMessage(String pattern, String channel, String message){
        LOGGER.info(String.format("PMessage. Pattern: %s, Channel: %s, Msg: %s", pattern, channel, message));
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels){
        LOGGER.info("onSubscribe");
    }
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        LOGGER.info("onUnsubscribe");
    }


    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        LOGGER.info("onPUnsubscribe");
    }


    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        LOGGER.info("onPSubscribe");
    }
}
