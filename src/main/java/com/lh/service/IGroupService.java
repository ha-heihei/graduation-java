package com.lh.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.entity.GroupMaterial;
import com.lh.entity.GroupUser;
import com.lh.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
public interface IGroupService extends IService<Group> {

    Boolean insertGroupInfo(Group group);

    Boolean deleteGroupById(Group group);

    Page<Group> querySelfGroupPageListByConditions(Group group);

    Page<Group> querySelfJoinGroupPageList(Group group);

    Boolean uploadGroupMaterial(GroupMaterial groupMaterial);

    Page<GroupMaterial> queryGroupSelfMaterials(GroupMaterial groupMaterial);

    Boolean deleteGroupMaterialsByIds(GroupMaterial groupMaterial);

    Boolean updateGroupMaterialById(GroupMaterial groupMaterial);

    Page<GroupMaterial> queryGroupMaterials(GroupMaterial groupMaterial);

    Page<GroupUser> queryGroupUserPageList(Group group);

    Page<Group> queryGroupInfoPageListByConditions(Group group);

    Page<Group> querySelfCreateGroupPageList(Group group);

    Boolean logoutGroup(GroupUser groupUser);

    Group queryOneGroupDetail(Group group);

    Page<User> queryNotInGroupUserList(Group group);
}
