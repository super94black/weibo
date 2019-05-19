package com.hunter.spittr.controller;

import com.hunter.spittr.meta.User;
import com.hunter.spittr.service.UserService;
import com.hunter.spittr.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
            //如果是管理用户 直接进入后台界面
            if(user.getType() == 2)
                return "redirect:/admin/check";
            return "redirect:/user/"+ user.getUsername();
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@Valid User user,
                        Model model,
                        HttpSession session,
                        HttpServletResponse response) {

        //验证用户名和密码是否正确
        user = spitterService.verifySpitter(user);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            model.addAttribute("user",user);

            String url = "http://localhost:8080/admin/check?pageNum=1";

            return Result.ok(url);
        }

        return Result.error("用户或密码错误");
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("user");

        return "redirect:/login";
    }
}
