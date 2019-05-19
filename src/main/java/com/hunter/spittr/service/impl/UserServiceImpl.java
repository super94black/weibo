package com.hunter.spittr.service.impl;

import com.hunter.spittr.meta.Image;
import com.hunter.spittr.meta.User;
import com.hunter.spittr.service.UserService;
import com.hunter.spittr.dao.UserDao;
import com.hunter.spittr.util.MD5Util;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Transactional
    public boolean isRegistered(User user) {
        //如果用户名或昵称已被注册，返回false
        if (userDao.getByUsername(user.getUsername()) != null) {
            return true;
        }
        return false;
    }

    //将用户信息添加到数据库
    @Transactional
    public void register(User user) throws Exception{
        user.setPassword(MD5Util.generate(user.getPassword()));
        user.setType(1);
        user.setCreate_time(new Timestamp(System.currentTimeMillis()));
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
    public User getUser(User user) {

        return userDao.getUser(user);
    }

    //将密码加盐计算MD5，验证密码正确性
    @Transactional
    public User verifySpitter(User user) {

        String password = userDao.getPassword(user.getUsername());

        //如果用户存在 且 密码正确，返回 包含完整信息的用户对象
        if(password != null && MD5Util.verify(user.getPassword(), password)){
            user.setPassword(password);
            return getUser(user);
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

        if(userDao.getByUsername(username) != null) {
            return "该用户名已被注册";
        }
        return "用户名可用";
    }

    @Override
    public String validateNickname(String nickname) {

        if(userDao.getByNickname(nickname) != null) {
            return "该昵称已被注册";
        }
        return "昵称可用";
    }

    public Image uploadImage(HttpServletRequest request, MultipartFile icon){
        //获取webapp部署的目录，函数的参数 是根目录下的子目录路径
        String contextPath = request.getSession().getServletContext().getRealPath("/");
        //创建图片目录
        File iconDir = new File(contextPath + "images/headIcon");
        File thumbnailDir = new File(contextPath + "images/thumbnails/headIcon");
        if (!iconDir.exists()) {
            iconDir.mkdirs();
        }
        if (!thumbnailDir.exists()) {
            thumbnailDir.mkdirs();
        }
        /**
         * 以 上传时间+文件原名 为名保存图片，
         * getTime 方法返回 代表从1970年1月1日开始（unix系统的时间戳起点）计算到Date对象中的时间之间的毫秒数。
         */
        String fileName = icon.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        fileName = new Date().getTime() + suffix;

        String iconUrl = "images/headIcon/" + fileName;
        String thumbnailUrl = "images/thumbnails/headIcon/" + fileName;
        String dbIconUrl = "http://localhost:8080/images/headIcon/" + fileName;
        String dbthumbnailUrl = "http://localhost:8080/images/thumbnails/headIcon/" + fileName;
        String iconAddress = contextPath + iconUrl;
        String thumbnailAddress = contextPath + thumbnailUrl;

        try {
            icon.transferTo(new File(iconAddress));
            Thumbnails.of(iconAddress).size(180, 180).toFile(iconAddress);
            Thumbnails.of(iconAddress).size(50, 50).toFile(new File(thumbnailAddress));
        }catch (Exception e){
            e.printStackTrace();
        }

        Image image = new Image();
        image.setIconUrl(dbIconUrl);
        image.setThumbnailUrl(dbthumbnailUrl);
        return image;
    }

    public String uploadVideo(MultipartFile video) throws IOException {
        //获取文件初始名称
        String originalFileName = video.getOriginalFilename();
        String houzhui = originalFileName.substring(originalFileName.lastIndexOf("."));

        //上传文件
        String newFileName = UUID.randomUUID()+houzhui;

        File newFile = new File("D:\\Program Files\\nginx\\nginx-rtmp-module\\tmp\\rec",newFileName);
        video.transferTo(newFile);
        return newFileName;
    }

    public User getUserById(int id){
        User user = userDao.getByUserId(id);
        return user;
    }


}
