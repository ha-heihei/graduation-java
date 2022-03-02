package com.lh.service;

import com.lh.entity.GroupMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihao
 * @since 2022-01-19
 */
public interface IGroupMessageService extends IService<GroupMessage> {

    Boolean inviteUserToGroup(GroupMessage message);

    List<GroupMessage> querySelfMessage(GroupMessage groupMessage);

    Long querySelfMessageCount(GroupMessage message);

    Boolean updateMessageStatus(GroupMessage message);

    Boolean requestJoinGroup(GroupMessage message);

    List<GroupMessage> queryAllMsgByConditions(GroupMessage message);
}
