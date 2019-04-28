package com.hunter.spittr.service;

import com.hunter.spittr.meta.User;

public interface UserService {
    boolean isRegistered(User user);

    void register(User user)throws Exception;

    User getByNickname(String nickname);

    User getByUsername(String username);

    User getSpitter(User user);

    User verifySpitter(User user);

    void updateUserInfo(User user);

    String validateUsername(String username);
}
