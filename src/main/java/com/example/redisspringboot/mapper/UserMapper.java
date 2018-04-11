package com.example.redisspringboot.mapper;

import com.example.redisspringboot.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/1/30
 */
@Mapper
@Repository
public interface UserMapper {

    @Insert("INSERT INTO user (name, pwd) VALUES (#{name},#{pwd})")
    void addUser(User user);

    @Select("SELECT * FROM user WHERE name = #{name}")
    User getUserByName(String name);

    @Select("SELECT * FROM user WHERE name = #{id}")
    User getUserById(int id);

    @Delete("DELETE FROM user WHERE name = #{name}")
    void delUserByName(@Param(value = "name") String name);
}
