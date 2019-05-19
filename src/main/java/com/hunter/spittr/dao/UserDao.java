package com.hunter.spittr.dao;

import com.hunter.spittr.meta.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserDao {
    //添加用户
    @Insert("INSERT INTO user(headIcon, thumbnail, username, password, nickname,create_time,type) " +
            "VALUE (#{headIcon}, #{thumbnail}, #{username}, #{password}, #{nickname},#{create_time},#{type})")
    void addSpitter(User user);

    //验证用户名或昵称是否已被注册
    @Select("SELECT * FROM user WHERE username=#{username} OR nickname=#{nickname}")
    User getByUsernameOrNickname(User user);


    //修改用户信息(头像、昵称)
    @Update("UPDATE user SET " +
            "headIcon=#{headIcon}, thumbnail=#{thumbnail}, nickname=#{nickname} " +
            "WHERE id=#{userId}")
    void updateUserInfo(User user);

    @Select("SELECT * FROM User WHERE id=#{userId}")
    User getByUserId(int userId);

    //获取用户信息，展示于用户个人主页
    @Select("SELECT * FROM User WHERE nickname=#{nickname}")
    User getByNickname(String nickname);

    //获取完整的用户信息（id，头像等）
    @Select("SELECT * FROM User WHERE username=#{username}")
    User getUser(User user);

    @Select("SELECT password From User WHERE username=#{username}")
    String getPassword(String username);

    @Select("SELECT * From User WHERE username=#{username}")
    User getByUsername(String username);
}
