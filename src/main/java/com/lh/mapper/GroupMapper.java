package com.lh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.entity.GroupMaterial;
import com.lh.entity.GroupUser;
import com.lh.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {

    Page<Group> querySelfGroupPageListByConditions(Page<Group> page,
                                                   @Param("ew")QueryWrapper<Group> wrapper,
                                                   @Param("userId")String userId);

    Page<Group> querySelfJoinGroupPageList(Page<Group> page,
                                             @Param("ew")QueryWrapper<Group> wrapper,
                                             @Param("userId")String userId);


    Page<GroupMaterial> queryGroupSelfMaterials(Page<GroupMaterial> page,
                                                @Param("userId")String userId,
                                                @Param("groupId")String groupId);

    Page<GroupMaterial> queryGroupMaterials(Page<GroupMaterial> page,
                                            @Param("groupId")String groupId);

    Page<GroupUser> queryGroupUserPageList(Page<GroupUser> page,
                                           @Param("groupId")String groupId);

    Page<Group> queryGroupInfoPageListByConditions(Page<Group> page,
                                                   @Param("ew")QueryWrapper<Group> wrapper);

    Page<Group> querySelfCreateGroupPageList(Page<Group> page,
                                             @Param("ew")QueryWrapper<Group> wrapper);

    Page<User> queryNotInGroupUserList(Page<User> page,
                                       @Param("groupId")String groupId,
                                       @Param("ew")QueryWrapper<User> wrapper);

    List<Group> queryAllGroupByConditions(@Param(Constants.WRAPPER)QueryWrapper<Group> wrapper);

}
