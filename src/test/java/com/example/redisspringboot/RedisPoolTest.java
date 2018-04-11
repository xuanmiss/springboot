package com.example.redisspringboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/3/30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisPoolTest {

    private static final Logger logger = LoggerFactory.getLogger("RedisPoolTest");
    @Autowired
    private JedisPool jedisPool;

    @Test
    public void testResouece()
    {
        for(int i=0;i<10;i++) {
            Jedis jedis = jedisPool.getResource();
            jedis.set(i+"",""+i);
            logger.info(i+"");
        }

        Jedis jedis = jedisPool.getResource();
        jedis.set("11","11");
        logger.info("11");
    }

}
