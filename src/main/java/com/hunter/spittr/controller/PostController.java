package com.hunter.spittr.controller;

import com.hunter.spittr.meta.Image;
import com.hunter.spittr.meta.Post;
import com.hunter.spittr.service.PostService;
import com.hunter.spittr.service.UserService;
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
            String videoName = "";
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





}
