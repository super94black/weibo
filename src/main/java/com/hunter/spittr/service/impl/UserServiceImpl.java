package com.hunter.spittr.service.impl;

import com.hunter.spittr.meta.User;
import com.hunter.spittr.service.UserService;
import com.hunter.spittr.dao.UserDao;
import com.hunter.spittr.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Transactional
    public boolean isRegistered(User user) {
        //如果用户名或昵称已被注册，返回false
        if (userDao.getByUsernameOrNickname(user) != null) {
            return true;
        }
        return false;
    }

    //将用户信息添加到数据库
    @Transactional
    public void register(User user) throws Exception{
        user.setPassword(MD5Util.generate(user.getPassword()));

        userDao.addSpitter(user);
    }

    //获取用户信息，展示于用户个人主页
    @Transactional
    public User getByNickname(String nickname) {
        return userDao.getByNickname(nickname);
    }

    @Transactional
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    //根据用户名和密码，获取完整的用户信息
    @Transactional
    public User getSpitter(User user) {

        return userDao.getSpitter(user);
    }

    //将密码加盐计算MD5，验证密码正确性
    @Transactional
    public User verifySpitter(User user) {

        String password = userDao.getPassword(user.getUsername());

        //如果用户存在 且 密码正确，返回 包含完整信息的用户对象
        if(password != null && MD5Util.verify(user.getPassword(), password)){
            user.setPassword(password);
            return getSpitter(user);
        }

        return null;
    }

    //更新用户个人信息
    @Transactional
    public void updateUserInfo(User user) {
        userDao.updateUserInfo(user);
    }

    @Override
    public String validateUsername(String username) {
        User user = new User();
        user.setUsername(username);
        if(userDao.getByUsernameOrNickname(user) != null) {
            return "该用户名已被注册";
        }
        return "用户名可用";
    }
}
