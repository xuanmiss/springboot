package com.example.redisspringboot.service;

import com.example.redisspringboot.Listener.RedisMsgPubSubListener;
import com.example.redisspringboot.entity.User;
import com.example.redisspringboot.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/1/30
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Jedis jedis;

    @CachePut(value = "user", key = "#root.targetClass + #user.name")
    public User addUser(User user)
    {
         userMapper.addUser(user);
         return userMapper.getUserByName(user.getName());
    }

    @Cacheable(value = "user", key = "#root.targetClass + #name", unless = "#result eq null")
    public User getUserByName(String name)
    {
        logger.info("从数据库取第一次的数据");
        return userMapper.getUserByName(name);
    }

    @CacheEvict(value = "user", key = "#root.targetClass + #name")
    public void delUserByName(String name)
    {
        userMapper.delUserByName(name);
//        return userMapper.getUserById(id);
    }

    @CacheEvict(value = "user",allEntries = true)
    public void clearAll()
    {

        new Thread(()->{
                RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
                jedis.subscribe(listener,"__keyevent@0__:del");
        }).start();
        logger.debug("**************************正在清除缓存 user");
    }
}
