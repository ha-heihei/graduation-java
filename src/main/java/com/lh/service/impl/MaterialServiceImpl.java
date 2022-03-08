package com.lh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.*;
import com.lh.mapper.GroupMaterialMapper;
import com.lh.mapper.MaterialMapper;
import com.lh.mapper.MaterialUserMapper;
import com.lh.mapper.UserMapper;
import com.lh.service.IMaterialService;
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
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private MaterialUserMapper materialUserMapper;

    @Resource
    private GroupMaterialMapper groupMaterialMapper;

    @Transactional
    @Override
    public Boolean insertMaterial(Material material) {
        material.setCreateTime(LocalDateTime.now());
        material.setMaterialId(String.valueOf(System.currentTimeMillis()));
        MaterialUser materialUser = new MaterialUser();
        materialUser.setCreateTime(LocalDateTime.now());
        materialUser.setMaterialId(material.getMaterialId());
        materialUser.setUserId(material.getUserId());
        materialUser.setMaterialName(material.getMaterialName());
        if(StringUtils.isBlank(material.getMaterialName())){
            material.setMaterialName("未命名");
            materialUser.setMaterialName("未命名");
        }
        if(StringUtils.isBlank(material.getRemarks())){
            materialUser.setRemarks("未描述");
        }else{
            materialUser.setRemarks(material.getRemarks());
        }
        return materialMapper.insert(material)==1&&materialUserMapper.insert(materialUser)==1;
    }

    @Override
    @Transactional
    public Boolean deleteMaterialById(Material material) {
        UpdateWrapper<MaterialUser> updateWrapper = new UpdateWrapper<MaterialUser>()
                .eq("user_id", material.getUserId())
                .eq("material_id", material.getMaterialId());
        return materialUserMapper.delete(updateWrapper)==1;
    }

    @Override
    public Boolean updateMaterialById(MaterialUser materialUser) {
        UpdateWrapper<MaterialUser> updateWrapper = new UpdateWrapper<MaterialUser>()
                .eq("material_id", materialUser.getMaterialId())
                .eq("user_id", materialUser.getUserId())
                .set("remarks", materialUser.getRemarks())
                .set("material_name", materialUser.getMaterialName());

        return materialUserMapper.update(null,updateWrapper)==1;
    }

    @Override
    public Page<Material> querySelfMaterialPageListByConditions(User user) {
        Page<User> page = new Page<>(user.getPage(), user.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("mu.user_id",user.getUserId())
                .like(StringUtils.isNotBlank(user.getMaterialName()), "mu.material_name", user.getMaterialName())
                .ge(Objects.nonNull(user.getBeginTime()), "mu.create_time", user.getBeginTime())
                .le(Objects.nonNull(user.getEndTime()), "mu.create_time", user.getEndTime())
                .orderByDesc("mu.create_time");
        Page<Material> materialPage = materialMapper.querySelfMaterialPageListByConditions(page, queryWrapper);
        for (Material material:materialPage.getRecords()){
            material.setGroupList(materialMapper.querySelfMaterialWhereGroup(user.getUserId(),material.getMaterialId()));
        }
        return materialPage;
    }

    @Override
    public Page<Material> queryCollectorMaterialPageList(User user) {
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().eq("user_type", "2"));
        ArrayList<String> userIds = new ArrayList<>();
        userList.forEach(item->{
            userIds.add(item.getUserId());
        });
        Page<Material> page = new Page<>(user.getPage(), user.getLimit());
        QueryWrapper<Material> queryWrapper = new QueryWrapper<Material>()
                .eq("u.user_type",2)
                .in(userIds.size()>0,"mu.user_id", userIds);

        return materialMapper.queryCollectorMaterialPageList(page,queryWrapper);
    }

    @Override
    public Boolean transferMaterials(MaterialUser materialUser) {
        materialUser.setCreateTime(LocalDateTime.now());
        if(StringUtils.isBlank(materialUser.getMaterialName())){
            Material material = materialMapper.selectOne(new QueryWrapper<Material>().eq("material_id", materialUser.getMaterialId()));
            materialUser.setMaterialName(material.getMaterialName());
        }
        if(StringUtils.isBlank(materialUser.getRemarks())){
            materialUser.setRemarks("");
        }
        return materialUserMapper.insert(materialUser)==1;
    }

    @Override
    public List<Material> queryAllUserMaterial(Material material) {
        QueryWrapper<Material> queryWrapper = new QueryWrapper<Material>()
                .like(StringUtils.isNotBlank(material.getMaterialName()), "mu.material_name", material.getMaterialName())
                .like(StringUtils.isNotBlank(material.getUserName()), "u.user_name", material.getUserName())
                .le(Objects.nonNull(material.getEndTime()), "mu.create_time", material.getEndTime())
                .ge(Objects.nonNull(material.getBeginTime()), "mu.create_time", material.getBeginTime());
        return materialMapper.queryAllUserMaterial(queryWrapper);
    }

    @Override
    public List<PublicMaterial> queryAllPublicMaterial(Material material) {
        QueryWrapper<PublicMaterial> queryWrapper = new QueryWrapper<PublicMaterial>()
                .like(StringUtils.isNotBlank(material.getUserName()), "u.user_name", material.getUserName())
                .like(StringUtils.isNotBlank(material.getMaterialName()), "pm.material_name", material.getMaterialName())
                .le(Objects.nonNull(material.getEndTime()), "pm.create_time", material.getEndTime())
                .ge(Objects.nonNull(material.getBeginTime()), "pm.create_time", material.getBeginTime());
        return materialMapper.queryAllPublicMaterial(queryWrapper);
    }

    @Transactional
    @Override
    public Boolean materialFusion(Material material) {
        GroupMaterial groupMaterial = new GroupMaterial();
        material.setMaterialId(String.valueOf(System.currentTimeMillis()));
        material.setCreateTime(LocalDateTime.now());
        // 工作组素材添加
        groupMaterial.setGroupId(material.getGroupId());
        groupMaterial.setMaterialId(material.getMaterialId());
        groupMaterial.setMaterialName(material.getMaterialName());
        groupMaterial.setGroupMaterialType(2);
        groupMaterial.setCreateTime(material.getCreateTime());
        groupMaterial.setUserId(material.getUserId());
        return groupMaterialMapper.insert(groupMaterial)==1&&materialMapper.insert(material)==1;
    }


}
