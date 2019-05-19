package com.hunter.spittr.controller;

import com.hunter.spittr.meta.Image;
import com.hunter.spittr.meta.User;
import com.hunter.spittr.service.UserService;
import com.hunter.spittr.util.Result;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        /**
         jsp页面的<sf:form>通过modelAttribute(旧用commandName)构建针对某个模型对象"user"的上下文信息，
         其他的表单绑定标签中会引用这个模型对象的属性
         在模型中必须要有一个key为"user"的对象，否则表单无法正常渲染
         因此往视图模型中添加一个key为"user"的Spitter对象
         */

        model.addAttribute("user", new User());
        return "registerForm";
    }


    @ResponseBody
    @RequestMapping(value = "/validateUsername", method = RequestMethod.POST, produces = "application/html;charset=utf-8")
    public String validateUsername(@RequestParam("username") String username){
        return userService.validateUsername(username);
    }

    @ResponseBody
    @RequestMapping(value = "/validateNickname", method = RequestMethod.POST, produces = "application/html;charset=utf-8")
    public String validateNickname(@RequestParam("nickname") String nickname){
        return userService.validateNickname(nickname);
    }




    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(HttpServletRequest request,
                           @RequestPart("icon") MultipartFile icon,
                           @Valid User user,
                           Errors errors,
                           HttpServletResponse response) throws IOException {
        //如果校验出现错误，则重新返回表单
        if (errors.hasErrors()) {
            return Result.error(errors.getAllErrors().toString());
        }
        //需要检验用户名是否已存在（需要改进为通过validator实现），若用户名未存在，将新用户的相关信息插入数据库
        boolean isRegistered = userService.isRegistered(user);
        if (isRegistered) {
            return Result.error("用户名已经被注册");
        }
        //需要检验用户名是否已存在（需要改进为通过validator实现），若用户名未存在，将新用户的相关信息插入数据库
        User dbUser = userService.getByNickname(user.getNickname());
        if (null != dbUser && !"".equals(dbUser.getNickname())) {
            return Result.error("昵称已经被注册");
        }

        //判断是否有上传图片
        if (!icon.isEmpty()) {
            Image image = userService.uploadImage(request,icon);
            //将图片的地址存入对应的spitter
            user.setHeadIcon(image.getIconUrl());
            user.setThumbnail(image.getThumbnailUrl());
        }else{
            String defualImg = "http://localhost:8080/images/headIcon/weibo.jpg";
            user.setHeadIcon(defualImg);
            user.setThumbnail(defualImg);
        }

        //注册用户
        try {
            userService.register(user);
        }catch (Exception e){
            e.printStackTrace();
        }


        //获取有完整内容的user对象（即含userId)
        user = userService.getUser(user);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        System.out.println(user.getUsername());
        String redirectUrl = "http://localhost:8080/user/" + user.getUsername() + "?pageNum=1";
        return Result.ok(redirectUrl);

    }
    




}