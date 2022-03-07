package com.lh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.common.CommonResult;
import com.lh.entity.User;
import com.lh.service.IUserService;
import com.lh.utils.OSSUtils;
import com.lh.utils.RandomValidateCodeUtils;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 插入用户信息,映射bean不可以加requestBody注解
     * requestBody注解只能接受JSON数据，无法接受mutipart/form-data类型
     * @param user
     * @return
     */
    @ApiModelProperty("添加用户信息")
    @PostMapping(value = "/insertUserInfo")
    public CommonResult insertUserInfo(User user,
                                       @RequestPart(value = "avatar",required = false)MultipartFile avatar) throws IOException {
        if(null== user || user.infoIsEmpty()){
            return CommonResult.fail("未填写用户信息");
        }
        if(userService.count(new QueryWrapper<User>().eq("user_name", user.getUserName()))>=1){
            return CommonResult.fail("用户名重复");
        }
        if(avatar!=null&&!avatar.isEmpty()){
            user.setUserImgUrl(OSSUtils.uploadFile(avatar.getInputStream()));
        }
        return userService.insertUserInfo(user)?CommonResult.success("添加成功"):CommonResult.fail("添加失败");
    }

    @ApiModelProperty("用户登陆")
    @PostMapping(value = "/login")
    public CommonResult login(User user, HttpSession session,
                              @RequestParam(value = "fromDevice",required = false)String fromDevice){
        String code= (String) session.getAttribute(RandomValidateCodeUtils.RANDOMCODEKEY);
        if(user==null|| StringUtils.isBlank(user.getUserPwd())||StringUtils.isBlank(user.getUserName())){
            return CommonResult.fail("请输入用户名和密码");
        }else if(StringUtils.isBlank(fromDevice)&&(code==null||user.getCode()==null||!code.equalsIgnoreCase(user.getCode()))){
            return CommonResult.fail("验证码错误");
        }
        user=userService.checkLogin(user);
        if(user==null){
            return CommonResult.fail("账号或密码错误");
        }else if(StringUtils.equals(String.valueOf(user.getUserStatus()),"0")){
            return CommonResult.fail("您的账号已被禁用");
        }
        user.setUserPwd(null);
        return CommonResult.success(user);
    }

    @ApiModelProperty("获得验证码")
    @GetMapping(value = "/getCodeImg")
    public void getCodeImg(HttpServletRequest request,HttpServletResponse response){
        RandomValidateCodeUtils codeUtils = new RandomValidateCodeUtils();
        codeUtils.getRandomCode(request,response);
    }

    @ApiModelProperty("查询一个用户详细信息")
    @PostMapping(value = "/queryOneUserInfoById")
    public CommonResult queryOneUserInfoById(User user){
        if(user==null||StringUtils.isBlank(user.getUserId())){
            return CommonResult.fail("未包含用户ID");
        }
        User userOne = userService.getById(user.getUserId());
        if(userOne==null){
            return CommonResult.fail("用户信息不存在");
        }
        userOne.setUserPwd("");
        return CommonResult.success(userOne);
    }

    /**
     * 多条件分页查询
     * @param user
     * @return
     */
    @ApiModelProperty("多条件分页查询用户信息")
    @PostMapping(value = "/queryPageUserInfoByConditions")
    public CommonResult queryPageUserInfoByConditions(User user){
        Page<User> userPage = userService.queryPageUserInfoByConditions(user);
        return CommonResult.success(userPage);
    }

    @ApiModelProperty("修改用户信息")
    @PostMapping(value = "/updateUserInfoById")
    public CommonResult updateUserInfoById(User user,
                                           @RequestPart(value = "avatar",required = false)MultipartFile avatar) throws IOException {
        if(user==null||StringUtils.isBlank(user.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        if(avatar!=null&&!avatar.isEmpty()){
            user.setUserImgUrl(OSSUtils.uploadFile(avatar.getInputStream()));
        }else{
            user.setUserImgUrl(null);
        }
        if(StringUtils.isNotBlank(user.getUserPwd())){
            user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
        }
        user.setUpdateTime(LocalDateTime.now());
        return userService.updateById(user)?CommonResult.success("更新成功"):CommonResult.success("更新失败");
    }

    /**
     * 传入用户ID列表
     * @param user
     * @return
     */
    @ApiModelProperty("删除用户信息")
    @PostMapping(value = "/deleteUserByIds")
    public CommonResult deleteUserByIds(User user){
        if(user==null||user.getIds()==null||user.getIds().size()==0){
            return CommonResult.fail("未传入用户ID");
        }
        return userService.removeBatchByIds(user.getIds())?CommonResult.success("删除成功"):CommonResult.fail("删除失败");
    }

    @ApiModelProperty("更新用户头像")
    @PostMapping(value = "/updateUserAvatar")
    public CommonResult updateUserAvatar(@RequestPart(value = "avatar",required = false)MultipartFile avatar,User user){
        if(avatar==null||avatar.isEmpty()){
            return CommonResult.fail("用户头像文件为空");
        }
        if(user==null||StringUtils.isBlank(user.getUserId())){
            return CommonResult.fail("用户ID为空");
        }
        try {
            user.setUserImgUrl(OSSUtils.uploadFile(avatar.getInputStream()));
            return userService.updateById(user)?CommonResult.success("",user.getUserImgUrl()):CommonResult.fail("上传失败，稍后重试");
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail("上传失败，稍后重试");
        }
    }


    @ApiOperation("用户行为信息统计")
    @PostMapping(value = "/statistics")
    public CommonResult statistics(User user){
        if(user==null||StringUtils.isBlank(user.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        return CommonResult.success(userService.statistics(user));
    }


}

