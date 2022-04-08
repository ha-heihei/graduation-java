package com.lh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.*;
import com.lh.mapper.*;
import com.lh.service.IGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private GroupUserMapper groupUserMapper;

    @Resource
    private GroupMaterialMapper groupMaterialMapper;

    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GroupMessageMapper groupMessageMapper;


    @Override
    public Boolean insertGroupInfo(Group group) {
        group.setGroupId(String.valueOf(System.currentTimeMillis()));
        group.setCreateTime(LocalDateTime.now());
        if(StringUtils.isBlank(group.getGroupDescription())){
            group.setGroupDescription("");
        }
        GroupUser groupUser = new GroupUser();
        groupUser.setUserId(group.getUserId());
        groupUser.setGroupId(group.getGroupId());
        groupUser.setCreateTime(LocalDateTime.now());
        return groupMapper.insert(group)==1&&groupUserMapper.insert(groupUser)==1;
    }

    @Override
    @Transactional
    public Boolean deleteGroupById(Group group) {
        int i = groupMapper.deleteById(group);
        if(i<=0){
            return false;
        }
        UpdateWrapper<GroupUser> groupUserUpdateWrapper = new UpdateWrapper<GroupUser>()
                .eq("group_id", group.getGroupId());
        groupUserMapper.delete(groupUserUpdateWrapper);
        groupMaterialMapper.delete(new UpdateWrapper<GroupMaterial>()
                .eq("group_id",group.getGroupId()));
        return true;
    }

    @Override
    public Page<Group> querySelfGroupPageListByConditions(Group group) {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<Group>()
                .like(StringUtils.isNotBlank(group.getGroupName()), "g.group_name", group.getGroupName())
                .ge(Objects.nonNull(group.getBeginTime()), "gu.create_time", group.getBeginTime())
                .le(Objects.nonNull(group.getEndTime()), "gu.create_time", group.getEndTime())
                .orderByDesc("gu.create_time");
        Page<Group> page=new Page<>(group.getPage(),group.getLimit());
        return groupMapper.querySelfGroupPageListByConditions(page,queryWrapper,group.getUserId());
    }

    @Override
    public Page<Group> querySelfJoinGroupPageList(Group group) {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<Group>()
                .like(StringUtils.isNotBlank(group.getGroupName()), "g.group_name", group.getGroupName())
                .ge(Objects.nonNull(group.getBeginTime()), "gu.create_time", group.getBeginTime())
                .le(Objects.nonNull(group.getEndTime()), "gu.create_time", group.getEndTime())
                .orderByDesc("gu.create_time");
        Page<Group> page=new Page<>(group.getPage(),group.getLimit());
        return groupMapper.querySelfJoinGroupPageList(page,queryWrapper,group.getUserId());
    }

    @Override
    public Boolean uploadGroupMaterial(GroupMaterial groupMaterial) {
        if(null==groupMaterial.getGroupMaterialType()){
            groupMaterial.setGroupMaterialType(1);
        }
        groupMaterial.setCreateTime(LocalDateTime.now());
        return groupMaterialMapper.insert(groupMaterial)==1;
    }

    @Override
    public Page<GroupMaterial> queryGroupSelfMaterials(GroupMaterial groupMaterial) {
        Page<GroupMaterial> page = new Page<>(groupMaterial.getPage(), groupMaterial.getLimit());
        return groupMapper.queryGroupSelfMaterials(page,groupMaterial.getUserId(),groupMaterial.getGroupId());
    }

    @Override
    @Transactional
    public Boolean deleteGroupMaterialsByIds(GroupMaterial groupMaterial) {
        UpdateWrapper<GroupMaterial> updateWrapper = new UpdateWrapper<GroupMaterial>()
                .eq("group_id", groupMaterial.getGroupId())
                .in("material_id", groupMaterial.getIds());
        return groupMaterialMapper.delete(updateWrapper)>=1&&materialMapper.deleteBatchIds(groupMaterial.getIds())>=1;
    }

    @Override
    public Boolean updateGroupMaterialById(GroupMaterial groupMaterial) {
        UpdateWrapper<Material> updateWrapper = new UpdateWrapper<Material>()
                .eq("material_id", groupMaterial.getMaterialId())
                .set("material_name", groupMaterial.getMaterialName());
        return materialMapper.update(null,updateWrapper)==1;
    }

    @Override
    public Page<GroupMaterial> queryGroupMaterials(GroupMaterial groupMaterial) {
        return groupMapper.queryGroupMaterials(new Page<>(groupMaterial.getPage(),groupMaterial.getLimit()),
                groupMaterial.getGroupId());
    }

    @Override
    public Page<GroupMaterial> queryGroupMaterialsNew(GroupMaterial groupMaterial) {
        return groupMapper.queryGroupMaterialsNew(new Page<>(groupMaterial.getPage(),groupMaterial.getLimit()),
                groupMaterial.getGroupId(),1);
    }

    @Override
    public Page<GroupUser> queryGroupUserPageList(Group group) {
        return groupMapper.queryGroupUserPageList(new Page<>(group.getPage(),group.getLimit()),
                group.getGroupId());
    }

    @Override
    public Page<Group> queryGroupInfoPageListByConditions(Group group) {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<Group>()
                .like(StringUtils.isNotBlank(group.getGroupName()), "group_name", group.getGroupName())
                .ge(Objects.nonNull(group.getBeginTime()), "wg.create_time", group.getBeginTime())
                .le(Objects.nonNull(group.getEndTime()), "wg.create_time", group.getEndTime())
                .orderByDesc("wg.create_time");
        Page<Group> page = groupMapper.queryGroupInfoPageListByConditions(new Page<>(group.getPage(), group.getLimit()),
                queryWrapper);
        for(Group ele:page.getRecords()){
            ele.setPeopleNum(Integer.parseInt(String.valueOf(groupUserMapper.selectCount(new QueryWrapper<GroupUser>()
                    .eq("group_id",ele.getGroupId())))));
            ele.setMaterialNum(Integer.parseInt(String.valueOf(groupMaterialMapper.selectCount(new QueryWrapper<GroupMaterial>()
                    .eq("group_id",ele.getGroupId())))));
            ele.setGroupMaterials(groupMapper.queryGroupMaterials(new Page<GroupMaterial>(-1,-1),ele.getGroupId()).getRecords());
            if(!StringUtils.isBlank(group.getUserId())){
                if(groupUserMapper.selectCount(new QueryWrapper<GroupUser>()
                        .eq("group_id",ele.getGroupId())
                        .eq("user_id",group.getUserId()))>0){
                    ele.setJoinStatus(1);
                    ele.setJoinMsg("已加入");
                }else if(groupMessageMapper.selectCount(new QueryWrapper<GroupMessage>()
                        .eq("originator_id",group.getUserId())
                        .eq("group_id",ele.getGroupId())
                        .eq("message_status",1))>0){
                    ele.setJoinStatus(2);
                    ele.setJoinMsg("已申请");
                }else{
                    ele.setJoinMsg("立即申请");
                    ele.setJoinStatus(0);
                }
            }
        }
        return page;
    }

    @Override
    public Page<Group> querySelfCreateGroupPageList(Group group) {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<Group>()
                .eq("wg.user_id", group.getUserId());
        Page<Group> page = new Page<>(group.getPage(), group.getLimit());
        return groupMapper.querySelfCreateGroupPageList(page,queryWrapper);
    }

    @Override
    public Boolean logoutGroup(GroupUser groupUser) {
        UpdateWrapper<GroupUser> updateWrapper = new UpdateWrapper<GroupUser>()
                .eq("user_id", groupUser.getUserId())
                .eq("group_id", groupUser.getGroupId());
        return groupUserMapper.delete(updateWrapper)==1;
    }

    @Override
    public Group queryOneGroupDetail(Group group) {
        Group groupDetail = groupMapper.selectById(group.getGroupId());
        User createUser = userMapper.selectById(groupDetail.getUserId());
        groupDetail.setUserImgUrl(createUser.getUserImgUrl());
        groupDetail.setUserName(createUser.getUserName());
        List<GroupUser> groupUsers = groupUserMapper.selectList(new QueryWrapper<GroupUser>().eq("group_id", group.getGroupId()));
        ArrayList<User> users = new ArrayList<>();
        for(GroupUser groupUser:groupUsers){
            User user = userMapper.selectById(groupUser.getUserId());
            user.setGroupSelfMaterials(groupMapper.queryGroupSelfMaterials(new Page<>(-1,-1),user.getUserId(),group.getGroupId()).getRecords());
            users.add(user);
        }
        groupDetail.setGroupUsers(users);

        return groupDetail;
    }

    @Override
    public Page<User> queryNotInGroupUserList(Group group) {
        return groupMapper.queryNotInGroupUserList(new Page<User>(group.getPage(),group.getLimit()),
                group.getGroupId(),new QueryWrapper<User>()
                        .like(StringUtils.isNotBlank(group.getUserName()),"user_name",group.getUserName())
                        .eq(StringUtils.isNotBlank(group.getMobile()),"mobile",group.getMobile()));
    }

    @Override
    public List<Group> queryAllGroupByConditions(Group group) {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<Group>()
                .like(StringUtils.isNotBlank(group.getGroupName()), "g.group_name", group.getGroupName())
                .like(StringUtils.isNotBlank(group.getUserName()), "u.user_name", group.getUserName())
                .le(Objects.nonNull(group.getEndTime()), "create_time", group.getEndTime())
                .ge(Objects.nonNull(group.getBeginTime()), "create_time", group.getBeginTime())
                .le(Objects.nonNull(group.getEndUserNum()), "userNum", group.getEndUserNum())
                .ge(Objects.nonNull(group.getBeginUserNum()), "userNum", group.getBeginUserNum())
                .le(Objects.nonNull(group.getEndMaterialNum()), "materialNum", group.getEndUserNum())
                .ge(Objects.nonNull(group.getBeginUserNum()), "materialNum", group.getBeginUserNum());
        return groupMapper.queryAllGroupByConditions(queryWrapper);
    }

    @Override
    public List<GroupMaterial> queryAllGroupMaterial(GroupMaterial groupMaterial) {
        QueryWrapper<GroupMaterial> queryWrapper = new QueryWrapper<GroupMaterial>()
                .like(StringUtils.isNotBlank(groupMaterial.getMaterialName()), "m.material_name", groupMaterial.getMaterialName())
                .like(StringUtils.isNotBlank(groupMaterial.getUserName()), "u.user_name", groupMaterial.getUserName())
                .eq(Objects.nonNull(groupMaterial.getGroupMaterialType()), "gm.group_material_type", groupMaterial.getGroupMaterialType())
                .le(Objects.nonNull(groupMaterial.getEndTime()), "gm.create_time", groupMaterial.getEndTime())
                .ge(Objects.nonNull(groupMaterial.getBeginTime()), "gm.create_time", groupMaterial.getBeginTime());
        List<GroupMaterial> groupMaterials = groupMapper.queryAllGroupMaterial(queryWrapper);
        for(GroupMaterial bean:groupMaterials){
            List<GroupMaterial> materials = groupMaterialMapper.selectList(new QueryWrapper<GroupMaterial>().eq("material_id", bean.getMaterialId()));
            ArrayList<String> list = new ArrayList<>();
            for(GroupMaterial material:materials){
                Group group = groupMapper.selectOne(new QueryWrapper<Group>().eq("group_id", material.getGroupId()));
                if(group!=null){
                    list.add(group.getGroupName());
                }
            }
            bean.setGroupList(list);
        }
        return groupMaterials;
    }
}
