package com.lh.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lh.common.CommonResult;
import com.lh.entity.GroupMessage;
import com.lh.service.IGroupMessageService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lihao
 * @since 2022-01-19
 */
@RestController
@RequestMapping("/groupMessage")
public class GroupMessageController {

    @Resource
    private IGroupMessageService groupMessageService;


    @ApiModelProperty("邀请用户加入工作组")
    @PostMapping(value = "/inviteUserToGroup")
    public CommonResult inviteUserToGroup(GroupMessage message){
        if(message==null|| StringUtils.isBlank(message.getGroupId())||
                StringUtils.isBlank(message.getOriginatorId())||StringUtils.isBlank(message.getReceiverId())){
            return CommonResult.fail("未包含工作组ID、邀请者ID、接受者ID");
        }
        return groupMessageService.inviteUserToGroup(message)?CommonResult.success("邀请成功"):CommonResult.fail("邀请失败");
    }

    @ApiModelProperty("用户申请加入")
    @PostMapping(value = "/requestJoinGroup")
    public CommonResult requestJoinGroup(GroupMessage message){
        if(message==null||StringUtils.isBlank(message.getOriginatorId())||
                StringUtils.isBlank(message.getGroupId())){
            return CommonResult.fail("未包含工作组ID、发起者ID");
        }
        return groupMessageService.requestJoinGroup(message)?CommonResult.success("请求成功"):CommonResult.fail("请求失败");
    }


    @ApiModelProperty("查看我的消息")
    @PostMapping(value = "/querySelfMessage")
    public CommonResult querySelfMessage(GroupMessage groupMessage){
        if(groupMessage==null||StringUtils.isBlank(groupMessage.getUserId())){
            return CommonResult.fail("未包含用户ID");
        }
        return CommonResult.success(groupMessageService.querySelfMessage(groupMessage));
    }

    @ApiModelProperty("修改信息状态")
    @PostMapping(value = "/updateMessageStatus")
    public CommonResult updateMessageStatus(GroupMessage message){
        if(message==null||StringUtils.isBlank((message.getMessageId()))||
                Objects.isNull(message.getMessageStatus())){
            return CommonResult.fail("未包含状态码、消息ID");
        }
        return groupMessageService.updateMessageStatus(message)?CommonResult.success("修改成功"):CommonResult.fail("修改失败");
    }

    @ApiModelProperty("查看我的消息数量")
    @PostMapping(value = "/querySelfMessageCount")
    public CommonResult querySelfMessageCount(GroupMessage message){
        if(message==null||StringUtils.isBlank(message.getUserId())){
            return CommonResult.fail("未包含用户ID");
        }
        return CommonResult.success(groupMessageService.querySelfMessageCount(message));
    }



}

