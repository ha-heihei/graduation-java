package com.lh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.entity.Group;
import com.lh.entity.GroupMessage;
import com.lh.entity.GroupUser;
import com.lh.entity.User;
import com.lh.mapper.GroupMapper;
import com.lh.mapper.GroupMessageMapper;
import com.lh.mapper.GroupUserMapper;
import com.lh.mapper.UserMapper;
import com.lh.service.IGroupMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lihao
 * @since 2022-01-19
 */
@Service
public class
GroupMessageServiceImpl extends ServiceImpl<GroupMessageMapper, GroupMessage> implements IGroupMessageService {

    @Resource
    private GroupMessageMapper messageMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GroupUserMapper groupUserMapper;

    @Resource
    private GroupMapper groupMapper;


    @Override
    public Boolean inviteUserToGroup(GroupMessage message) {
        message.setMessageId(String.valueOf(System.currentTimeMillis()));
        message.setMessageStatus(1);
        if(message.getRemarks()==null){
            message.setRemarks("邀请您加入");
        }
        return messageMapper.insert(message)==1;
    }

    @Override
    public List<GroupMessage> querySelfMessage(GroupMessage groupMessage) {
        QueryWrapper<GroupMessage> queryWrapper = new QueryWrapper<GroupMessage>()
//                .eq("gm.originator_id", groupMessage.getUserId())
//                .or()
                .eq("gm.receiver_id", groupMessage.getUserId())
                .orderByDesc("message_id");
        List<GroupMessage> groupMessages = messageMapper.querySelfMessage(queryWrapper);
        for(GroupMessage message:groupMessages){
            User originator = userMapper.selectById(message.getOriginatorId());
            message.setOriginator(originator);
            User receiver = userMapper.selectById(message.getReceiverId());
            message.setReceiver(receiver);
        }
        return groupMessages;
    }

    @Override
    public Long querySelfMessageCount(GroupMessage message) {
        QueryWrapper<GroupMessage> queryWrapper = new QueryWrapper<GroupMessage>()
                .eq("message_status", 1)
                .eq("receiver_id", message.getUserId());
        return messageMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional
    public Boolean updateMessageStatus(GroupMessage message) {

        if(message.getMessageStatus()==2) {
            GroupMessage msg = messageMapper.selectById(message.getMessageId());
            GroupUser groupUser = new GroupUser();
            groupUser.setGroupId(msg.getGroupId());
            Group group = groupMapper.selectById(groupUser.getGroupId());
            //如果用户id与工作组创建者id相同，则说明是别人申请加入，否则为邀请加入其他工作组
            if(Objects.equals(group.getUserId(),msg.getReceiverId())){
                groupUser.setUserId(msg.getOriginatorId());
            }else{
                groupUser.setUserId(msg.getReceiverId());
            }
            groupUser.setCreateTime(LocalDateTime.now());
            return groupUserMapper.insert(groupUser) == 1&&messageMapper.deleteById(message.getMessageId())==1;
        }
        return messageMapper.deleteById(message.getMessageId())==1;

    }

    @Override
    public Boolean requestJoinGroup(GroupMessage message) {
        Group group=groupMapper.selectById(message.getGroupId());
        if(group==null){
            return false;
        }
        message.setReceiverId(group.getUserId());
        message.setMessageId(String.valueOf(System.currentTimeMillis()));
        message.setMessageStatus(1);
        if(message.getRemarks()==null){
            message.setRemarks("请求加入");
        }
        return messageMapper.insert(message)==1;
    }
}
