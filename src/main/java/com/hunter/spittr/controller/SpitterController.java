package com.hunter.spittr.controller;

import com.hunter.spittr.service.SpitterService;
import com.hunter.spittr.meta.Spitter;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Controller
public class SpitterController {
    @Resource
    private SpitterService spitterService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        /**
         jsp页面的<sf:form>通过modelAttribute(旧用commandName)构建针对某个模型对象"spitter"的上下文信息，
         其他的表单绑定标签中会引用这个模型对象的属性
         在模型中必须要有一个key为"spitter"的对象，否则表单无法正常渲染
         因此往视图模型中添加一个key为"spitter"的Spitter对象
         */

        model.addAttribute("spitter", new Spitter());
        return "registerForm";
    }


    @ResponseBody
    @RequestMapping(value = "/validateUsername", method = RequestMethod.POST, produces = "application/html;charset=utf-8")
    public String validateUsername(@RequestParam("username") String username){

        return spitterService.validateUsername(username);
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request,
                           @RequestPart("icon") MultipartFile icon,
                           @Valid Spitter spitter,
                           Errors errors,
                           HttpServletResponse response) throws IOException {
        //如果校验出现错误，则重新返回表单
        if (errors.hasErrors()) {

            return "registerForm";
        }
        //需要检验用户名是否已存在（需要改进为通过validator实现），若用户名未存在，将新用户的相关信息插入数据库
        boolean isRegistered = spitterService.isRegistered(spitter);
        if (isRegistered) {
            return "login";
        }

        //判断是否有上传图片
        if (!icon.isEmpty()) {
            //获取webapp部署的目录，函数的参数 是根目录下的子目录路径
            String contextPath = request.getSession().getServletContext().getRealPath("/");
            //创建图片目录
            File iconDir = new File(contextPath + "images/headIcon");
            File thumbnailDir = new File(contextPath + "images/thumbnails/headIcon");
            if (!iconDir.exists()) {
                iconDir.mkdirs();
            }
            if (!thumbnailDir.exists()) {
                thumbnailDir.mkdirs();
            }
            /**
             * 以 上传时间+文件原名 为名保存图片，
             * getTime 方法返回 代表从1970年1月1日开始（unix系统的时间戳起点）计算到Date对象中的时间之间的毫秒数。
             */
            String fileName = icon.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf('.'));
            fileName = new Date().getTime() + suffix;

            String iconUrl = "images/headIcon/" + fileName;
            String thumbnailUrl = "images/thumbnails/headIcon/" + fileName;
            String iconAddress = contextPath + iconUrl;
            String thumbnailAddress = contextPath + thumbnailUrl;

            icon.transferTo(new File(iconAddress));

            Thumbnails.of(iconAddress).size(180, 180).toFile(iconAddress);
            Thumbnails.of(iconAddress).size(50, 50).toFile(new File(thumbnailAddress));

            //将图片的地址存入对应的spitter
            spitter.setHeadIcon(iconUrl);
            spitter.setThumbnail(thumbnailUrl);

        } else {
            //提供默认的初始用户头像
        }

        //注册用户
        try {
            spitterService.register(spitter);
        }catch (Exception e){
            e.printStackTrace();
        }


        //获取有完整内容的spitter对象（即含userId)
        spitter = spitterService.getSpitter(spitter);

        Cookie userId = new Cookie("userId", String.valueOf(spitter.getId()));
        response.addCookie(userId);

        HttpSession session = request.getSession();
        session.setAttribute("spitter", spitter);
        /**
         *
         *  username作为占位符填充到url模板中，比起String形式直连更安全
         *  模型中其他所有的 原始类型值 都可以添加到URL中作为查询参数，
         *  如果没有相应的占位符，会自动以查询参数的形式附加到重定向URL上。
         */
        System.out.println(spitter.getUsername());
        return "redirect:/" + spitter.getUsername() + "?pageNum=1";//redirect的路径实际还会加上 ?spitterId=xxxx
    }

//    更新用户信息

}