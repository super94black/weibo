package com.hunter.spittr.service;

import com.hunter.spittr.meta.Image;
import com.hunter.spittr.meta.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface UserService {
    boolean isRegistered(User user);

    void register(User user)throws Exception;

    User getByNickname(String nickname);

    User getByUsername(String username);

    User getUser(User user);

    User verifySpitter(User user);

    void updateUserInfo(User user);

    String validateUsername(String username);
    public String validateNickname(String nickname);

    public Image uploadImage(HttpServletRequest request, MultipartFile icon);

    public String uploadVideo(MultipartFile video) throws IOException;

    public User getUserById(int id);
}
