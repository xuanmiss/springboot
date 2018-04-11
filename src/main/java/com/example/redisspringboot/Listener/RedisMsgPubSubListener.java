package com.example.redisspringboot.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;
/**
 * @author miss
 * <p>
 * Created by miss on 2018/1/29
 */
public class RedisMsgPubSubListener extends JedisPubSub {

    private final static Logger logger = LoggerFactory.getLogger(RedisMsgPubSubListener.class);

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }

    @Override
    public void unsubscribe(String... channels) {
        super.unsubscribe(channels);
    }

    @Override
    public void subscribe(String... channels) {
        super.subscribe(channels);
    }

    @Override
    public void psubscribe(String... patterns) {
        super.psubscribe(patterns);
    }

    @Override
    public void punsubscribe() {
        super.punsubscribe();
    }

    @Override
    public void punsubscribe(String... patterns) {
        super.punsubscribe(patterns);
    }

    @Override
    public void onMessage(String channel, String message) {
//        System.out.println("channel:" + channel + "receives message :" + message);
        logger.info("channel:" + channel + " receives message : " + message);
//        this.unsubscribe();
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
//        System.out.println("channel:" + channel + "is been subscribed:" + subscribedChannels);
        logger.info("channel:" + channel + " is been subscribed: " + subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("channel:" + channel + "is been unsubscribed:" + subscribedChannels);
        logger.info("channel:" + channel + " is been unsubscribed: " + subscribedChannels);
    }
}
