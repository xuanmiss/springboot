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
}
