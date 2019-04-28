package com.hunter.spittr.controller;

import com.hunter.spittr.meta.User;
import com.hunter.spittr.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Resource
    UserService spitterService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model,
                        HttpSession session) {

        //判断session中是否已有用户对象
        User user = (User)session.getAttribute("user");
        if (user != null){
            model.addAttribute("nickname", user.getNickname());
            return "redirect:/user/"+ user.getUsername();
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid User user,
                        Model model,
                        HttpSession session,
                        HttpServletResponse response) {

        //验证用户名和密码是否正确
        user = spitterService.verifySpitter(user);
        if (user != null) {
            Cookie userId = new Cookie("userId", String.valueOf(user.getId()));

            if(user.isAutoLogin()){//如果勾选了自动登录，则有效期跟session保持一致
                userId.setMaxAge(30*60);
            }
            response.addCookie(userId);
            model.addAttribute("nickname", user.getNickname());
            session.setAttribute("user", user);
            model.addAttribute("user",user);
            return "redirect:/user/" + user.getUsername() +"?pageNum=1";
        }

        model.addAttribute("msg","用户名或密码错误！");
        return "login";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("user");

        return "redirect:/login";
    }
}
