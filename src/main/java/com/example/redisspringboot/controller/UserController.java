package com.example.redisspringboot.controller;

import com.example.redisspringboot.Listener.RedisMsgPubSubListener;
import com.example.redisspringboot.entity.User;
import com.example.redisspringboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/1/30
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping("/addUser")
    public User addUser(
            @RequestBody(required = true) User user
    ){
        logger.debug(user.toString());
        return userService.addUser(user);

    }

    @GetMapping("/getUser")
    public User getUserByName(
            @RequestParam(value = "name")String name
    ){
                return userService.getUserByName(name);
    }

    @GetMapping("/delUser")
    public void delUser(
            @RequestParam(value = "name")String name
    ){
                logger.debug(name);
                userService.delUserByName(name);
//                logger.debug(id+"");
    }

    @GetMapping("/clearAll")
    public void delAll(){
        userService.clearAll();
    }

}
