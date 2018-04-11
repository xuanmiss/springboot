package com.example.redisspringboot;

import com.example.redisspringboot.Listener.RedisMsgPubSubListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.TimeUnit;

/**
 * @author miss
 * <p>测试Redis事件监听
 * Created by miss on 2018/1/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    RedisSerializer serializer;

    @Autowired
    private Jedis jedis;
//    @Autowired
//    private JedisConnectionFactory jedisConnectionFactory;

    @Test
    public void set()
    {
        jedis.set("hello","world~~~~~~~~~~");

    }

    @Test
    public void get()
    {
        String value = stringRedisTemplate.opsForValue().get("hello");

            logger.debug(value);
    }

    /**
     * 测试过期事件 订阅
     * @throws Exception
     */
    @Test
    public void testSubscribe() throws Exception{
        serializer = redisTemplate.getDefaultSerializer();
        RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
        jedis.subscribe(listener,"__keyevent@0__:expired");

    }


    /**
     * 写入一个带过期时间的操作
     * @throws Exception
     */
    @Test
    public void setExp() throws Exception
    {
        String key = "hello";
        String value = "world";
        RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
        for(int i = 0;i<3;i++) {
            TimeUnit.SECONDS.sleep(2);
            jedis.setex(key+i, 8, value+i);
            logger.info(key+i+"******"+value+i);
        }
        jedis.subscribe(listener,"__keyevent@0__:expired");
    }
    @Test
    public void del()
    {
//        jedis.set("hello","world");
        logger.debug("****************"+jedis.del("hello"));
    }


    /**
     * 测试过期事件 发布
     * @throws Exception
     */
    @Test
    public void testPublish() throws Exception{
//        Jedis jedis = new Jedis("localhost");
        jedis.publish("redisChatTest", "我是天才");
//        TimeUnit.SECONDS.sleep(5);
        jedis.publish("redisChatTest", "我牛逼");
//        TimeUnit.SECONDS.sleep(5);
        jedis.publish("redisChatTest", "哈哈");
    }
}
