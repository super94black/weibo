package com.hunter.spittr.controller;

import com.github.pagehelper.PageInfo;
import com.hunter.spittr.meta.*;
import com.hunter.spittr.service.PostService;
import com.hunter.spittr.service.UserService;
import com.hunter.spittr.service.SpittleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {
    @Resource
    private SpittleService spittleService;
    @Resource
    private UserService userService;
    @Autowired
    private PostService postService;

    //获取动态列表
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String getSpittleList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        int userId = 0;
        if(null != user) {
            userId = (int) user.getId();
            System.out.println(user.getHeadIcon());
            model.addAttribute("headImg",user.getHeadIcon());
        }
        getAllPost(user,model,pageNum, userId,0);
        List<Post> list = null;
        try {
            list = postService.getHotTopic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("hotTopicList",list);
        return "index";
    }


    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String getSpittle(@RequestParam("uid") Integer uid,
                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             Model model,HttpSession session) {


        User user = (User) session.getAttribute("user");

        if(null != user) {

            model.addAttribute("headImg",user.getHeadIcon());
        }

        getAllPost(user,model,pageNum, uid,1);
        return "userPostList";





    }




    //发送动态
    @RequestMapping(method = RequestMethod.POST)
    public String publishSpittle(@Valid Spittle spittle,
                                 Errors errors,
                                 Model model,
                                 HttpSession session,
                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        //如果校验出现错误，则重新返回表单
        if (errors.hasErrors()) {
            return "index";
        }
        User spitter = (User)session.getAttribute("spitter");
        spittleService.publishSpittle(
                new Spittle(spittle.getMessage(), new Date(), spitter.getId(),
                        spitter.getNickname(), spitter.getThumbnail()));

        PageInfo pageInfo = spittleService.getSpittleList(pageNum);
        model.addAttribute("pageInfo", pageInfo);

        return "index";
    }

    //展示用户详情页（包含用户个人资料 和 发布过的动态）
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String showSpitterProfile(@PathVariable("username") String username,
                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     Model model,HttpSession session) {



        //展示具体用户主页的基本信息
        User user = userService.getByUsername(username);

        if(null == username || "".equals(username) || null == user){
            return "index";
        }
        session.setAttribute("user",user);
        getAllPost(user,model,pageNum, (int) user.getId(),1);
        return "profile";
    }

    public void getAllPost(User user,Model model,int pageNum,int userId,int type){
        model.addAttribute("user", user);
        PageVo<Post> pageVo = postService.getAllPost(pageNum, userId,type);
        model.addAttribute("map",pageVo.getMap());
        model.addAttribute("pageInfo", pageVo.getPageInfo());
    }






}
