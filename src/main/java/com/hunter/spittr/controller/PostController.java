package com.hunter.spittr.controller;

import com.hunter.spittr.meta.PostPo;
import com.hunter.spittr.meta.PostVo;
import com.hunter.spittr.service.PostService;
import com.hunter.spittr.util.JsonUtils;
import com.hunter.spittr.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2019/4/25 20:59
 * @Content
 */
@Controller
public class PostController {


    @Autowired
    PostService postService;

    @RequestMapping("/post")

    public void getAllPost(Model model){
        Map<PostPo,List<PostPo>> map = postService.getAllPost(0);
        model.addAttribute("map",map);

    }
}
