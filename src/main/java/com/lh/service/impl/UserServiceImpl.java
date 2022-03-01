package com.lh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.*;
import com.lh.mapper.*;
import com.lh.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Resource
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Resource
    private UserMapper userMapper;

    @Resource
    private MaterialUserMapper materialUserMapper;

    @Resource
    private GroupMaterialMapper groupMaterialMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private GroupUserMapper groupUserMapper;

    @Override
    public Boolean insertUserInfo(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", user.getMobile());
        if(userMapper.selectCount(queryWrapper)>=1){
            return false;
        }
        user.setCreateTime(LocalDateTime.now());
        user.setUserStatus(1);
        user.setUserId(String.valueOf(System.currentTimeMillis()));
        if(StringUtils.isBlank(user.getUserPwd())){
            user.setUserPwd(user.getMobile());
        }
        user.setUserPwd(cryptPasswordEncoder.encode(user.getUserPwd()));
        return userMapper.insert(user)==1;
    }

    @Override
    public User checkLogin(User user) {
        User one = userMapper.selectOne(new QueryWrapper<User>()
                .eq("user_name", user.getUserName()));
        if(one==null){
            return null;
        }
        if(cryptPasswordEncoder.matches(user.getUserPwd(),one.getUserPwd())){
            return one;
        }
        return null;
    }

    @Override
    public Page<User> queryPageUserInfoByConditions(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .like(StringUtils.isNotBlank(user.getUserName()), "user_name", user.getUserName())
                .like(StringUtils.isNotBlank(user.getMobile()), "mobile", user.getMobile())
                .like(StringUtils.isNotBlank(user.getEmail()), "email", user.getEmail())
                .eq(Objects.nonNull(user.getGender()), "gender", user.getGender())
                .ge(Objects.nonNull(user.getBeginTime()), "create_time", user.getBeginTime())
                .le(Objects.nonNull(user.getEndTime()), "create_time", user.getEndTime())
                .eq(Objects.nonNull(user.getUserStatus()), "user_status", user.getUserStatus())
                .eq(Objects.nonNull(user.getUserType()), "user_type", user.getUserType());
        return userMapper.selectPage(new Page<>(user.getPage(),user.getLimit()), queryWrapper);
    }

    @Override
    public User statistics(User user) {

        // 素材统计
        user.setSelfMaterialsNum(Integer.parseInt(String.valueOf(materialUserMapper.selectCount(new QueryWrapper<MaterialUser>()
                .eq("user_id",user.getUserId())))));
        user.setGroupSelfMaterialNum(Integer.parseInt(String.valueOf(groupMaterialMapper.selectCount(new QueryWrapper<GroupMaterial>()
                .eq("user_id",user.getUserId())))));

        // 工作组统计
        user.setSelfCreateGroupNum(Integer.parseInt(String.valueOf(groupMapper.selectCount(new QueryWrapper<Group>()
                .eq("user_id",user.getUserId())))));
        user.setSelfJoinGroupNum(Integer.parseInt(String.valueOf(groupUserMapper.selectCount(new QueryWrapper<GroupUser>()
                .eq("user_id",user.getUserId()))-user.getSelfCreateGroupNum())));

        // 查询过去七天的情况
        List<String> dateList = getPastDateList(7);
        HashMap<String, List<Integer>> materialStatistics = new HashMap<>();
        ArrayList<Integer> selfMaterialList = new ArrayList<>();
        ArrayList<Integer> selfGroupMaterialList = new ArrayList<>();
        HashMap<String, List<Integer>> groupStatistics = new HashMap<>();
        ArrayList<Integer> selfJoinGroupList = new ArrayList<>();
        ArrayList<Integer> selfCreateGroupList = new ArrayList<>();
        for(String date:dateList){
            QueryWrapper<MaterialUser> muWrapper = new QueryWrapper<MaterialUser>()
                    .eq("user_id", user.getUserId())
                    .eq("SUBSTRING_INDEX(create_time,' ',1)", date);
            QueryWrapper<GroupMaterial> gmWrapper = new QueryWrapper<GroupMaterial>()
                    .eq("user_id", user.getUserId())
                    .eq("SUBSTRING_INDEX(create_time,' ',1)", date);
            QueryWrapper<Group> gWrapper = new QueryWrapper<Group>()
                    .eq("user_id", user.getUserId())
                    .eq("SUBSTRING_INDEX(create_time,' ',1)", date);
            QueryWrapper<GroupUser> guWrapper = new QueryWrapper<GroupUser>()
                    .eq("user_id", user.getUserId())
                    .eq("SUBSTRING_INDEX(create_time,' ',1)", date);
            selfMaterialList.add(Integer.parseInt(String.valueOf(materialUserMapper.selectCount(muWrapper))));
            selfGroupMaterialList.add(Integer.parseInt(String.valueOf(groupMaterialMapper.selectCount(gmWrapper))));

            selfCreateGroupList.add(Integer.parseInt(String.valueOf(groupMapper.selectCount(gWrapper))));
            selfJoinGroupList.add(Integer.parseInt(String.valueOf(groupUserMapper.selectCount(guWrapper)-selfCreateGroupList.get(selfCreateGroupList.size()-1))));

        }
        materialStatistics.put("selfMaterial",selfMaterialList);
        materialStatistics.put("selfGroupMaterial",selfGroupMaterialList);

        groupStatistics.put("selfCreateGroup",selfCreateGroupList);
        groupStatistics.put("selfJoinGroup",selfJoinGroupList);

        user.setGroupStatistics(groupStatistics);
        user.setMaterialStatistics(materialStatistics);
        user.setDateList(dateList);
        return user;
    }

    private List<String> getPastDateList(int n){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ArrayList<String> dateList = new ArrayList<>();
        for(int i=0;i<n;i++){
            dateList.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DATE,-1);
        }
        Collections.reverse(dateList);
        return dateList;
    }

}
