package com.hunter.spittr.controller;

import com.hunter.spittr.meta.Image;
import com.hunter.spittr.meta.PostPo;
import com.hunter.spittr.service.PostService;
import com.hunter.spittr.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author zhang
 * @Date 2019/5/19 20:51
 * @Content
 */
@Controller
public class AdminController {

    @Autowired
    PostService postService;

    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public String getUncheckPost(Model model){
        List<PostPo> list = postService.getUncheckedPost();
        model.addAttribute("list",list);
        return "check";

    }

    @RequestMapping(value = "/check",method = RequestMethod.POST)
    @ResponseBody
    public Result acceptPost(@RequestParam("pid") Integer pid){
        try {
            if(pid == null || 0 == pid)
                return Result.error("pid不合法");
            postService.updateByPostId(pid);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("网络错误");
        }
        return Result.ok();

    }

}
