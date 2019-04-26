package com.hunter.spittr.controller;

import com.github.pagehelper.PageInfo;
import com.hunter.spittr.meta.*;
import com.hunter.spittr.service.PostService;
import com.hunter.spittr.service.SpitterService;
import com.hunter.spittr.service.SpittleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class SpittleController {
    @Resource
    private SpittleService spittleService;
    @Resource
    private SpitterService spitterService;
    @Autowired
    private PostService postService;

    //获取动态列表
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String getSpittleList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 Model model) {

        PageVo<Post> pageVo = postService.getAllPost(pageNum,0);
        model.addAttribute("map",pageVo.getMap());
        model.addAttribute("pageInfo", pageVo.getPageInfo());
        return "index";
    }

    //获取单条动态
//    @RequestMapping(value = "/{spittleId}", method = RequestMethod.GET)
//    public String getSpittle(@PathVariable("spittleId") long spittleId,
//                             Model model) {
//
//        Spittle spittle = spittleService.getSpittle(spittleId);
//
//        if (spittle == null) {
//
//            throw new SpittleNotFoundException();
//        }
//
//        model.addAttribute("spittle", spittle);
//        return "spittle";
//    }

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
        Spitter spitter = (Spitter)session.getAttribute("spitter");
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
                                     Model model) {

        //展示具体用户主页的基本信息
        Spitter spitter = spitterService.getByUsername(username);
        model.addAttribute("spitter", spitter);

        PageInfo pageInfo = spittleService.getSpittlesByUserId(pageNum, spitter.getId());
        model.addAttribute("pageInfo", pageInfo);

        return "profile";
    }
}
