package com.hunter.spittr.controller;

import com.hunter.spittr.service.ZanService;
import com.hunter.spittr.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhang
 * @Date 2019/4/28 17:34
 * @Content
 */
@Controller
public class ZanController {

    @Autowired
    ZanService zanService;

    @RequestMapping(value = "/zan",method = RequestMethod.POST)
    @ResponseBody
    public Result addZanByPidAndUid(int postId, int uid){
        try {
            zanService.addZanByPidAndUid(postId,uid);
        }catch (Exception e){
            Result.error();
        }
        return Result.ok();
    }

    @RequestMapping(value = "/disZan",method = RequestMethod.POST)
    @ResponseBody
    public Result delZanByPidAndUid(int postId, int uid){
        try {
            zanService.delZanByPostIdAndUid(postId,uid);
        }catch (Exception e){
            Result.error();
        }
        return Result.ok();
    }


}
