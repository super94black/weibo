package com.hunter.spittr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author zhang
 * @Date 2019/5/19 17:53
 * @Content
 */
@Controller

public class PageController {


    @RequestMapping(value = "/post",method = RequestMethod.GET)
    public String post() {

        return "post";
    }


}
