package com.example.redisspringboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/4/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisListTemTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisListTemTest.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testList()
    {
        Long l =redisTemplate.opsForList().leftPush("user","bb");
        logger.info(l.toString());
    }

    @Test
    public void testAllList()
    {
        String [] users = new String[]{"aa","bb","cc"};
        List<String> userlist = new ArrayList<String>();
        userlist.add("dd");
        userlist.add("ee");
        logger.info(redisTemplate.opsForList().leftPushAll("user",users).toString());
        logger.info(redisTemplate.opsForList().leftPushAll("user",userlist).toString());
        logger.info(redisTemplate.opsForList().range("user",0,-1).toString());
    }

    @Test
    public void testLeftPushIfPresent()
    {
        logger.info(redisTemplate.opsForList().leftPushIfPresent("notPresent","firstValue").toString());
        logger.info(redisTemplate.opsForList().leftPush("notPresent","secondValue").toString());
        logger.info(redisTemplate.opsForList().leftPushIfPresent("notPresent","thirdValue").toString());
    }

    @Test
    public void testSet()
    {
        logger.info(redisTemplate.opsForList().range("user",0,-1).toString());
        redisTemplate.opsForList().set("user",1,"newValue");
        logger.info(redisTemplate.opsForList().range("user",0,-1).toString());
    }

    @Test
    public void testLeftPop()
    {
        Object rs = redisTemplate.opsForList().leftPop("user");
        logger.info(rs.toString());
        logger.info(redisTemplate.opsForList().range("user",0,-1).toString());

    }

    @Test
    public void testLeftPushV1()
    {
        logger.info(redisTemplate.opsForList().range("newList",0,-1).toString());
        redisTemplate.opsForList().leftPush("newList","2","3");
        logger.info(redisTemplate.opsForList().range("newList",0,-1).toString());
    }

    @Test
    public void testRemove()
    {
        logger.info(redisTemplate.opsForList().range("newList",0,-1).toString());
        redisTemplate.opsForList().remove("newList",0,"2");
        logger.info(redisTemplate.opsForList().range("newList",0,-1).toString());
    }

    @Test
    public void testIndex()
    {
        logger.info(redisTemplate.opsForList().index("newList",1).toString());
    }
}
