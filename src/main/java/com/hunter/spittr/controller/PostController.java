package com.hunter.spittr.controller;

import com.hunter.spittr.meta.Image;
import com.hunter.spittr.meta.PageVo;
import com.hunter.spittr.meta.Post;
import com.hunter.spittr.meta.User;
import com.hunter.spittr.service.PostService;
import com.hunter.spittr.service.UserService;
import com.hunter.spittr.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @Author zhang
 * @Date 2019/4/25 20:59
 * @Content
 */
@Controller
public class PostController {


    @Autowired
    PostService postService;
    @Autowired
    UserService userService;


    @RequestMapping("/replay")
    @ResponseBody
    public Result replayPost(Post post){
        boolean flag = postService.replayPost(post);
        if(flag)
            return Result.ok();
        else
            return Result.error();
    }

    @RequestMapping("/post/del")
    @ResponseBody
    public Result deleteByPostId(int id){
        try {
            postService.deleteByPostId(id);
        }catch (Exception e){
            return Result.error();
        }
        return Result.ok();
    }


    /**
     * 发布动态
     * @param video
     * @param img
     * @param content
     * @param request
     * @param uid
     * @return
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public Result post(@RequestParam("file") MultipartFile video, @RequestParam("img") MultipartFile img,
                       String content, HttpServletRequest request,Integer uid){
        try {
            String videoName = null;
            Image image = new Image();

            if(null == content || "".equals(content))
                return Result.error("说说不能为空");

            if(!video.isEmpty())
                videoName = userService.uploadVideo(video);
            if(!img.isEmpty())
                image = userService.uploadImage(request,img);
             postService.post(uid,image.getIconUrl(),videoName,content);

        }catch (Exception e){
            return Result.error();
        }
        return Result.ok();
    }

    @RequestMapping(value = "/post",method = RequestMethod.GET)

    public String post(@RequestParam("postId") Integer postId, Model model, HttpSession session){
        if(null == postId)
            postId = 0;
        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("user",user);
            PageVo<Post> pageVo = postService.getPostById(1,postId);
            model.addAttribute("map",pageVo.getMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "topic";
    }




}
