package com.lh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LiHao
 * @create 2022-01-19 11:05
 */
@Controller
public class CommonController {

    @RequestMapping(value = {"/","/index","/index.html"})
    public String toIndex(){
        return "index";
    }

    @RequestMapping(value ="/login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping(value ="/toMyGroup")
    public String toMyGroup(){
        return "group/mygroup";
    }

    @RequestMapping(value ="/toBG")
    public String toBg(){
        return "bg";
    }

    @RequestMapping(value = "/toJoinGroup")
    public String toJoinGroup(){
        return "group/joingroup";
    }

    @RequestMapping(value = "/toMyMaterial")
    public String toMyMaterial(){
        return "material/mymaterial";
    }

    @RequestMapping(value = "/toPublicMaterial")
    public String toPublicMaterial(){
        return "material/publicmaterial";
    }

    @RequestMapping(value = "/toUserInfo")
    public String toUserInfo(){
        return "user/userinfo";
    }

    @RequestMapping(value = "/toMaterialBeautify")
    public String toMaterialBeautify(){
        return "material/materialbeautify";
    }

    @RequestMapping(value = "/toAdmin")
    public String toAdmin(){
        return "admin";
    }

    @RequestMapping(value = "/toUserList")
    public String toUserList(){
        return "admin/userlist";
    }

    @RequestMapping(value = "/toGroupMsg")
    public String toGroupMsg(){
        return "admin/groupmsg";
    }
}
