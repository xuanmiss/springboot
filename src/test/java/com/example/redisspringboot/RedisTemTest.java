package com.example.redisspringboot;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/4/2
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTemTest.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void getString()
    {
        String result = stringRedisTemplate.opsForValue().get("hello");
//        String rs = redisTemplate.opsForValue().get("hello");
        logger.info(result);
    }

    @Test
    public void setString()
    {
        stringRedisTemplate.opsForValue().set("0402test", "100",60*10, TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间

    }

    @Test
    public void setTestString()
    {
        stringRedisTemplate.opsForValue().set("hello","definesys");
        stringRedisTemplate.opsForValue().set("hello","pass",6);
        logger.info(stringRedisTemplate.opsForValue().get("hello"));
    }

    @Test
    public void setIfAbsentString()
    {
        String key = "hello1";
        String value = "define";
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key,value);
        if(flag)
        {
            logger.info("key***"+key+"不存在");
        }else{
            logger.info("key***"+key+"已存在，不覆盖");
        }
        logger.info(stringRedisTemplate.opsForValue().get(key));
    }

    @Test
    public void setAndGetTestString()
    {
        String re = stringRedisTemplate.opsForValue().getAndSet("hello1","newDefine");
        logger.info(re);
        logger.info(stringRedisTemplate.opsForValue().get("hello1"));
    }

    @Test
    public void incrementTestString()
    {
        stringRedisTemplate.opsForValue().increment("number",10.2);
        logger.info(stringRedisTemplate.opsForValue().get("number"));
    }

    @Test
    public void appendTestString()
    {
        Integer l= stringRedisTemplate.opsForValue().append("new","hello ");
        logger.info("长度是：***"+l+"***"+stringRedisTemplate.opsForValue().get("new"));
        Integer ll= stringRedisTemplate.opsForValue().append("new","World");
        logger.info("长度是：***"+ll+"***"+stringRedisTemplate.opsForValue().get("new"));
    }

    @Test
    public void getTestString()
    {
        String key = "hello";//value = definepass
        logger.info(stringRedisTemplate.opsForValue().get(key,0,5));
        logger.info(stringRedisTemplate.opsForValue().get(key,0,-1));
        logger.info(stringRedisTemplate.opsForValue().get(key,-4,-1));

    }

    @Test
    public void sizeTestString()
    {
        Long l = stringRedisTemplate.opsForValue().size("hello");
        logger.info(l.toString());
    }

    @Test
    public void getObject()
    {
        String res = stringRedisTemplate.opsForValue().get("auth:6731794e-aab8-455e-b8b9-f1816d2ab8a3");
        logger.info(res);

    }

    //redisTemplate 事务操作

    @Test
    @Transactional
    public void testRedisLimit()
    {
        Object o =stringRedisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //   operations.watch("testRedisMulti");
                operations.multi();
                operations.opsForValue().set("testRedisMulti", "0");
                String now = (String) operations.opsForValue().get("testRedisMulti");
                System.out.println(now);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                now = (String) operations.opsForValue().get("testRedisMulti");
                System.out.println(now);
                Object rs = operations.exec();
                return rs;
            }
        });
        System.out.println(stringRedisTemplate.opsForValue().get("testRedisMulti"));
        System.out.println(o);

//        return o;

    }
}
