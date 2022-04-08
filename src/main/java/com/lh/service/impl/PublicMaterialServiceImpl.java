package com.lh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.entity.Material;
import com.lh.entity.MaterialUser;
import com.lh.entity.PublicMaterial;
import com.lh.entity.User;
import com.lh.mapper.MaterialMapper;
import com.lh.mapper.MaterialUserMapper;
import com.lh.mapper.PublicMaterialMapper;
import com.lh.mapper.UserMapper;
import com.lh.service.IPublicMaterialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lihao
 * @since 2022-02-14
 */
@Service
public class PublicMaterialServiceImpl extends ServiceImpl<PublicMaterialMapper, PublicMaterial> implements IPublicMaterialService {

    @Resource
    private PublicMaterialMapper publicMaterialMapper;

    @Resource
    private MaterialUserMapper materialUserMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private MaterialMapper materialMapper;


    @Override
    @Transactional
    public Page<PublicMaterial> queryCollectorMaterialsPageList(PublicMaterial publicMaterial) {
        QueryWrapper<PublicMaterial> queryWrapper = new QueryWrapper<PublicMaterial>()
                .like(StringUtils.isNotBlank(publicMaterial.getMaterialName()), "pm.material_name", publicMaterial.getMaterialName())
                .eq(StringUtils.isNotBlank(publicMaterial.getUserName()), "u.user_name", publicMaterial.getUserName());
        // 如果不是采集员，则不是自己的素材
        User user = userMapper.selectById(publicMaterial.getUserId());
        if(user.getUserType()!=2){
            List<String> materialIds = materialUserMapper.selectList(new QueryWrapper<MaterialUser>().eq("user_id", publicMaterial.getUserId()))
                    .stream().map(MaterialUser::getMaterialId).collect(Collectors.toList());
            queryWrapper.notIn(materialIds.size() > 0,"pm.material_id",materialIds);
        }else{
            // 如果是采集员，则查询采集员自己上传的素材
            queryWrapper.eq("pm.user_id",publicMaterial.getUserId());
        }
        // 排序规则
        if(publicMaterial.getSortType()==1){
            if(publicMaterial.getSortOrder()==0){
                queryWrapper.orderByAsc("search_num");
            }else{
                queryWrapper.orderByDesc("search_num");
            }
        }else if(publicMaterial.getSortType()==2){
            if(publicMaterial.getSortOrder()==0){
                queryWrapper.orderByAsc("download_num");
            }else{
                queryWrapper.orderByDesc("download_num");
            }
        }else if(publicMaterial.getSortType()==3){
            if(publicMaterial.getSortOrder()==0){
                queryWrapper.orderByAsc("transform_num");
            }else{
                queryWrapper.orderByDesc("transform_num");
            }
        }else if(publicMaterial.getSortType()==4){
            if(publicMaterial.getSortOrder()==0){
                queryWrapper.orderByAsc("thumbs_num");
            }else{
                queryWrapper.orderByDesc("thumbs_num");
            }
        }else if(publicMaterial.getSortType()==5){
            if(publicMaterial.getSortOrder()==0){
                queryWrapper.orderByAsc("pm.create_time");
            }else{
                queryWrapper.orderByDesc("pm.create_time");
            }
        }else{
            queryWrapper.orderByDesc("(transform_num+download_num)*0.5+thumbs_num*0.3+search_num*0.2")
                        .orderByDesc("pm.create_time");
        }
        Page<PublicMaterial> page = publicMaterialMapper.queryCollectorMaterialsPageList(new Page<>(publicMaterial.getPage(), publicMaterial.getLimit()),
                                                                                            publicMaterial.getUserId(), queryWrapper);
        // 不是采集员，则搜索量+1
        if(StringUtils.isNotBlank(publicMaterial.getMaterialName())&&user.getUserType()!=2){
            for(PublicMaterial material:page.getRecords()){
                publicMaterialMapper.update(null,new UpdateWrapper<PublicMaterial>()
                        .eq("material_id",material.getMaterialId())
                        .set("search_num",material.getSearchNum()+1));
            }
        }
        return page;
    }

    @Override
    public Boolean insertMaterial(PublicMaterial material) {
        material.setSearchNum(0);
        material.setDownloadNum(0);
        material.setThumbsNum(0);
        material.setTransformNum(0);
        if(StringUtils.isBlank(material.getRemarks())){
            material.setRemarks("该用户没有描述该素材");
        }
        material.setCreateTime(LocalDateTime.now());
        material.setMaterialId(String.valueOf(System.currentTimeMillis()));
        return publicMaterialMapper.insert(material)==1;
    }

    @Override
    @Transactional
    public Boolean transformMaterial(PublicMaterial material) {
        PublicMaterial publicMaterial = publicMaterialMapper.selectById(material.getMaterialId());
        if(publicMaterial==null){
            return false;
        }
        Material selfMaterial = new Material();
        MaterialUser materialUser = new MaterialUser();
        //Material填写,公共素材库转移到material表
        selfMaterial.setMaterialName(publicMaterial.getMaterialName());
        selfMaterial.setUserId(material.getUserId());
        selfMaterial.setCreateTime(LocalDateTime.now());
        selfMaterial.setMaterialId(publicMaterial.getMaterialId());
        JSONObject materialUrls=new JSONObject();
        materialUrls.put("originUrl", publicMaterial.getMaterialUrl());
        selfMaterial.setMaterialUrls(materialUrls.toJSONString());
        //MaterialUser填写，填写前端传过来的名称，备注
        materialUser.setMaterialId(publicMaterial.getMaterialId());
        materialUser.setCreateTime(LocalDateTime.now());
        materialUser.setMaterialName(material.getMaterialName());
        materialUser.setUserId(material.getUserId());
        if(StringUtils.isBlank(material.getRemarks())){
            materialUser.setRemarks("未描述");
        }else{
            materialUser.setRemarks(material.getRemarks());
        }
        return materialUserMapper.insert(materialUser)==1&&
                materialMapper.insert(selfMaterial)==1&&
                publicMaterialMapper.update(null,new UpdateWrapper<PublicMaterial>()
                        .eq("material_id",material.getMaterialId())
                        .setSql("transform_num=transform_num+1"))==1;
    }

    @Override
    public Page<PublicMaterial> recommendMaterial(PublicMaterial material) {
        QueryWrapper<PublicMaterial> queryWrapper = new QueryWrapper<>();
        List<String> materialIds = materialUserMapper.selectList(new QueryWrapper<MaterialUser>().eq("user_id", material.getUserId()))
                .stream().map(MaterialUser::getMaterialId).collect(Collectors.toList());
        materialIds.addAll(material.getIds());
        queryWrapper.notIn(materialIds.size()>0,"pm.material_id",materialIds);
        queryWrapper.orderByDesc("(transform_num+download_num)*0.5+thumbs_num*0.3+search_num*0.2")
                .orderByDesc("pm.create_time");
        return publicMaterialMapper.queryCollectorMaterialsPageList(new Page<>(material.getPage(), material.getLimit()),
                null,queryWrapper);
    }


}
