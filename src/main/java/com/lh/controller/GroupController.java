package com.lh.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.common.CommonResult;
import com.lh.entity.Group;
import com.lh.entity.GroupMaterial;
import com.lh.entity.GroupUser;
import com.lh.entity.Material;
import com.lh.service.IGroupService;
import com.lh.service.IMaterialService;
import com.lh.service.ImageService;
import com.lh.utils.OSSUtils;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private IGroupService groupService;

    @Resource
    private IMaterialService materialService;

    @Resource
    private ImageService imageService;

    @ApiModelProperty("创建一个工作组，初始群组群主加入")
    @PostMapping(value = "/insertGroupInfo")
    public CommonResult insertGroupInfo(Group group,
                                        @RequestPart(value = "avatar",required = false)MultipartFile avatar) throws IOException {
        if(group==null|| StringUtils.isBlank(group.getUserId())||StringUtils.isBlank(group.getGroupName())){
            return CommonResult.fail("信息未填写完全");
        }
        if(avatar!=null&&!avatar.isEmpty()){
            String fileUrl = OSSUtils.uploadFile(avatar.getInputStream());
            group.setGroupImgUrl(fileUrl);
        }else{
            group.setGroupImgUrl(Group.DEFAULT_IMAGE_URL);
        }

        return groupService.insertGroupInfo(group)?CommonResult.success("添加成功"):CommonResult.fail("添加失败");
    }


    @ApiModelProperty("删除一个工作组以及移除组员")
    @PostMapping(value = "/deleteGroupById")
    public CommonResult deleteGroupById(Group group){
        if(group==null||StringUtils.isBlank(group.getGroupId())){
            return CommonResult.fail("未包含工作组ID");
        }
        return groupService.deleteGroupById(group)?CommonResult.success("删除成功"):CommonResult.fail("删除失败");
    }


    @ApiModelProperty("多条件分页查询我加入的工作组")
    @PostMapping(value = "/querySelfGroupPageListByConditions")
    public CommonResult querySelfGroupPageListByConditions(Group group){
        if(group==null||StringUtils.isBlank(group.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        return CommonResult.success(groupService.querySelfGroupPageListByConditions(group));
    }

    @ApiModelProperty("多条件分页查询仅仅加入的工作组")
    @PostMapping(value = "/querySelfJoinGroupPageList")
    public CommonResult querySelfJoinGroupPageList(Group group){
        if(group==null||StringUtils.isBlank(group.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        return CommonResult.success(groupService.querySelfJoinGroupPageList(group));
    }

    @ApiModelProperty("上传一个工作组素材")
    @PostMapping(value = "/uploadGroupMaterial")
    public CommonResult uploadGroupMaterial(GroupMaterial groupMaterial,
                                            @RequestPart(value = "materialFile",required = false)MultipartFile materialFile) throws IOException {
        if(groupMaterial==null||StringUtils.isBlank(groupMaterial.getGroupId())||
                StringUtils.isBlank(groupMaterial.getUserId())){
            return CommonResult.fail("未包含工作组ID、上传者ID");
        }
        if(StringUtils.isBlank(groupMaterial.getMaterialUrls())&&(materialFile==null||materialFile.isEmpty())){
            return CommonResult.fail("未上传素材");
        }
        if(StringUtils.isBlank(groupMaterial.getMaterialName())){
            groupMaterial.setMaterialName("未命名");
        }
        if(materialFile!=null&&!materialFile.isEmpty()){
            groupMaterial.setMaterialId(String.valueOf(System.currentTimeMillis()));
            Material material = new Material();
            material.setMaterialId(groupMaterial.getMaterialId());
            JSONObject object = new JSONObject();
            object.put("originUrl",OSSUtils.uploadFile(materialFile.getInputStream()));
            material.setMaterialUrls(object.toJSONString());
            material.setCreateTime(LocalDateTime.now());
            material.setUserId(groupMaterial.getUserId());
            material.setMaterialName(groupMaterial.getMaterialName());
            if(!materialService.save(material)){
                return CommonResult.fail("素材插入素材库失败");
            }
        }
        return groupService.uploadGroupMaterial(groupMaterial)?CommonResult.success("上传成功"):CommonResult.fail("上传失败");
    }


    @ApiModelProperty("分页查询工作组内某一个成员上传的素材")
    @PostMapping(value = "/queryGroupSelfMaterials")
    public CommonResult queryGroupSelfMaterials(GroupMaterial groupMaterial){
        if(groupMaterial==null||StringUtils.isBlank(groupMaterial.getUserId())||
                StringUtils.isBlank(groupMaterial.getGroupId())){
            return CommonResult.fail("未完全包含工作组ID、用户ID");
        }
        return CommonResult.success(groupService.queryGroupSelfMaterials(groupMaterial));
    }

    @ApiModelProperty("删除工作组内素材")
    @PostMapping(value = "/deleteGroupMaterialsByIds")
    public CommonResult deleteGroupMaterials(GroupMaterial groupMaterial){
        if(groupMaterial==null||groupMaterial.getIds()==null||
                groupMaterial.getIds().size()==0||StringUtils.isBlank(groupMaterial.getGroupId())){
            return CommonResult.fail("未包含素材IDS、工作组ID");
        }
        return groupService.deleteGroupMaterialsByIds(groupMaterial)?CommonResult.success("删除成功"):CommonResult.fail("删除失败");
    }

    @ApiModelProperty("修改工作组内素材")
    @PostMapping(value = "/updateGroupMaterialById")
    public CommonResult updateGroupMaterialById(GroupMaterial groupMaterial){
        if(groupMaterial==null|| StringUtils.isBlank(groupMaterial.getMaterialId())){
            return CommonResult.fail("未包含素材ID");
        }
        return groupService.updateGroupMaterialById(groupMaterial)?CommonResult.success("修改成功"):CommonResult.fail("修改失败");
    }

    @ApiModelProperty("分页查询工作组内素材")
    @PostMapping(value = "/queryGroupMaterials")
    public CommonResult queryGroupMaterials(GroupMaterial groupMaterial){
        if(groupMaterial==null||StringUtils.isBlank(groupMaterial.getGroupId())){
            return CommonResult.fail("未上传工作组ID");
        }
        return CommonResult.success(groupService.queryGroupMaterials(groupMaterial));
    }

    @ApiModelProperty("分页查询工作组内素材-新")
    @PostMapping(value = "/queryGroupMaterialsNew")
    public CommonResult queryGroupMaterialsNew(GroupMaterial groupMaterial){
        if(groupMaterial==null||StringUtils.isBlank(groupMaterial.getGroupId())){
            return CommonResult.fail("未上传工作组ID");
        }
        Page<GroupMaterial> materials = groupService.queryGroupMaterialsNew(groupMaterial);
        for(GroupMaterial material:materials.getRecords()){
            JSONObject object = (JSONObject) JSONObject.parse(material.getMaterialUrls());
            String originUrl = object.getString("originUrl");
            try {
                CommonResult bodySeg = imageService.bodySeg(originUrl);
                JSONObject result = new JSONObject((Map<String, Object>) bodySeg.getResult());
                material.setMaterialUrls(result.toJSONString());
            } catch (Exception e) {
                object.put("foregroundUrl","");
                object.put("maskUrl","");
                material.setMaterialUrls(object.toJSONString());
            }
        }
        return CommonResult.success(materials);
    }

    @ApiModelProperty("查询工作组内所有成员")
    @PostMapping(value = "/queryGroupUserPageList")
    public CommonResult queryGroupUserPageList(Group group){
        if(group==null||StringUtils.isBlank(group.getGroupId())){
            return CommonResult.fail("未上传工作组ID");
        }
        return CommonResult.success(groupService.queryGroupUserPageList(group));
    }

    @ApiModelProperty("多条件分页查询工作组")
    @PostMapping(value ="/queryGroupInfoPageListByConditions")
    public CommonResult queryGroupInfoPageListByConditions(Group group){
        Page<Group> page = groupService.queryGroupInfoPageListByConditions(group);
        return CommonResult.success(page);
    }

    @ApiModelProperty("查询我创建的工作组")
    @PostMapping(value = "/querySelfCreateGroupPageList")
    public CommonResult querySelfCreateGroupPageList(Group group){
        if(group==null||StringUtils.isBlank(group.getUserId())){
            return CommonResult.fail("未上传创建者ID");
        }
        return CommonResult.success(groupService.querySelfCreateGroupPageList(group));
    }

    @ApiModelProperty("修改自己创建工作组信息")
    @PostMapping(value = "/updateSelfGroupInfoById")
    public CommonResult updateSelfGroupInfoById(Group group,
                                                @RequestPart(value = "avatar",required = false)MultipartFile avatar) throws IOException {
        if(group==null||StringUtils.isBlank(group.getGroupId())){
            return CommonResult.fail("未上传工作组ID");
        }
        if(avatar!=null&&!avatar.isEmpty()){
            group.setGroupImgUrl(OSSUtils.uploadFile(avatar.getInputStream()));
        }
        if(StringUtils.isBlank(group.getGroupName())&&StringUtils.isBlank(group.getGroupDescription())&&
                StringUtils.isBlank(group.getGroupImgUrl())){
            return CommonResult.fail("没有可修改的信息");
        }
        return groupService.updateById(group)?CommonResult.success("更新成功"):CommonResult.fail("更新失败");
    }

    @ApiModelProperty("踢出、退出工作组")
    @PostMapping(value = "/logoutGroup")
    public CommonResult logoutGroup(GroupUser groupUser){
        if(groupUser==null||StringUtils.isBlank(groupUser.getUserId())||
                StringUtils.isBlank(groupUser.getGroupId())){
            return CommonResult.fail("未传入工作组ID、用户ID");
        }
        return groupService.logoutGroup(groupUser)?CommonResult.success("退出成功"):CommonResult.fail("退出失败");
    }


    @ApiModelProperty("查询一个工作组的详细信息")
    @PostMapping(value = "/queryOneGroupDetail")
    public CommonResult queryOneGroupDetail(Group group){
        if(group==null||StringUtils.isBlank(group.getGroupId())){
            return CommonResult.fail("未包含工作组ID");
        }
        return CommonResult.success(groupService.queryOneGroupDetail(group));
    }

    @ApiModelProperty("查询不在该工作组内且未消息验证的用户")
    @PostMapping(value = "/queryNotInGroupUserList")
    public CommonResult queryNotInGroupUserList(Group group){
        if(group==null||StringUtils.isBlank(group.getGroupId())){
            return CommonResult.fail("未包含工作组ID");
        }
        return CommonResult.success(groupService.queryNotInGroupUserList(group));
    }


    @ApiOperation("管理员查询所有的工作组")
    @PostMapping(value = "/queryAllGroupByConditions")
    public CommonResult queryAllGroupByConditions(Group group){
        return CommonResult.success(groupService.queryAllGroupByConditions(group));
    }

    @ApiOperation("管理员查询工作组内素材")
    @PostMapping(value = "/queryAllGroupMaterial")
    public CommonResult queryAllGroupMaterial(GroupMaterial groupMaterial){
        return CommonResult.success(groupService.queryAllGroupMaterial(groupMaterial));
    }



}

