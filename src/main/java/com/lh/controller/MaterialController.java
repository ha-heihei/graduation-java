package com.lh.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lh.common.CommonResult;
import com.lh.entity.Material;
import com.lh.entity.MaterialImg;
import com.lh.entity.MaterialUser;
import com.lh.entity.User;
import com.lh.service.IMaterialService;
import com.lh.service.IMaterialUserService;
import com.lh.service.ImageService;
import com.lh.utils.OSSUtils;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
@RestController
@RequestMapping("/material")
public class MaterialController {

    @Resource
    private IMaterialService materialService;

    @Resource
    private ImageService imageService;

    @Resource
    private IMaterialUserService materialUserService;

    @ApiModelProperty("添加一个素材")
    @PostMapping(value = "/insertMaterial")
    public CommonResult insertMaterial(Material material,
                                       @RequestPart(value = "material",required = false)MultipartFile materialImg){
        if(material==null|| StringUtils.isBlank(material.getMaterialName())||
                StringUtils.isBlank(material.getUserId())){
            return CommonResult.fail("用户ID、素材名称信息不完全");
        }
        if(StringUtils.isBlank(material.getMaterialUrls())&&(materialImg==null||materialImg.isEmpty())){
            return CommonResult.fail("未上传图片素材");
        }
        if(materialImg!=null&&!materialImg.isEmpty()){
            JSONObject object = new JSONObject();
            try {
                object.put("originUrl", OSSUtils.uploadFile(materialImg.getInputStream()));
                material.setMaterialUrls(object.toJSONString());
                return materialService.insertMaterial(material)?CommonResult.success("上传成功"):CommonResult.fail("上传失败");
            } catch (IOException e) {
                e.printStackTrace();
                return CommonResult.fail("上传失败");
            }
        }
        return materialService.insertMaterial(material)?CommonResult.success("上传成功"):CommonResult.fail("上传失败");
    }

    @ApiModelProperty("人脸美颜API，将美颜后的图像存入素材库")
    @PostMapping(value = "/faceBeautify")
    public CommonResult faceBeautify(Material material, @RequestParam("imgUrl")String imgUrl){
        if(material==null||StringUtils.isBlank(material.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        CommonResult commonResult = imageService.faceBeautify(imgUrl);
        if(commonResult==null||!commonResult.getSuccess()){
            return commonResult;
        }
        JSONObject object = new JSONObject();
        object.put("originUrl",commonResult.getResult());
        material.setMaterialUrls(object.toJSONString());
        return materialService.insertMaterial(material)?commonResult:CommonResult.fail("美颜失败");
    }

    @ApiModelProperty("删除material_user表中逻辑关系，不会真正删除素材")
    @PostMapping(value = "/deleteMaterialById")
    public CommonResult deleteMaterialById(Material material){
        if(material==null||StringUtils.isBlank(material.getUserId())||
                StringUtils.isBlank(material.getMaterialId())){
            return CommonResult.fail("参数用户ID、素材ID不全");
        }
        return materialService.deleteMaterialById(material)?CommonResult.success("删除成功"):CommonResult.fail("删除失败");
    }

    @ApiModelProperty("图像增强API，同人脸美颜")
    @PostMapping(value = "/definitionEnhance")
    public CommonResult definitionEnhance(Material material,@RequestParam("imgUrl")String imgUrl){
        if(material==null||StringUtils.isBlank(material.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        CommonResult commonResult = imageService.definitionEnhance(imgUrl);
        if(commonResult==null||!commonResult.getSuccess()){
            return commonResult;
        }
        JSONObject object = new JSONObject();
        object.put("originUrl",commonResult.getResult());
        material.setMaterialUrls(object.toJSONString());
        return materialService.insertMaterial(material)?commonResult:CommonResult.fail("图像增强失败");
    }

    @ApiModelProperty("图像锐化API，同人脸美颜")
    @PostMapping(value = "/contrastEnhance")
    public CommonResult contrastEnhance(Material material,@RequestParam("imgUrl")String imgUrl){
        if(material==null||StringUtils.isBlank(material.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        CommonResult commonResult = imageService.contrastEnhance(imgUrl);
        if(commonResult==null||!commonResult.getSuccess()){
            return commonResult;
        }
        JSONObject object = new JSONObject();
        object.put("originUrl",commonResult.getResult());
        material.setMaterialUrls(object.toJSONString());
        return materialService.insertMaterial(material)?commonResult:CommonResult.fail("图像锐化失败");
    }

    @ApiModelProperty("背景虚化API，同人脸美颜")
    @PostMapping(value = "/backGaussianBlur")
    public CommonResult backGaussianBlur(Material material,@RequestParam("imgUrl")String imgUrl){
        if(material==null||StringUtils.isBlank(material.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        CommonResult bodySeg = imageService.bodySeg(imgUrl);
        if(!bodySeg.getSuccess()){
            return bodySeg;
        }
        JSONObject object= new JSONObject((Map<String,Object>)bodySeg.getResult());
        CommonResult result = imageService.backGaussianBlur(object.getString("originUrl"),
                object.getString("maskUrl"),
                object.getString("foregroundUrl"));
        if(!result.getSuccess()){
            return CommonResult.fail("背景虚化失败");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("originUrl",result.getResult());
        material.setMaterialUrls(jsonObject.toJSONString());
        return materialService.insertMaterial(material)?result:CommonResult.fail("背景虚化失败");
    }

    @ApiOperation(value = "证件照合成")
    @PostMapping(value = "/backFill")
    public CommonResult backFill(Material material,
                                 @RequestParam("imgUrl")String imgUrl,
                                 @RequestParam("color")String color){
        if(material==null||StringUtils.isBlank(material.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        CommonResult bodySeg = imageService.bodySeg(imgUrl);
        if(!bodySeg.getSuccess()){
            return CommonResult.fail("背景分割失败");
        }
        JSONObject object= new JSONObject((Map<String,Object>)bodySeg.getResult());

        CommonResult result = imageService.backFill(object.getString("foregroundUrl"),
                object.getString("maskUrl"), color);
        if(!result.getSuccess()){
            return CommonResult.fail("证件照合成失败");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("originUrl",result.getResult());
        material.setMaterialUrls(jsonObject.toJSONString());
        return materialService.insertMaterial(material)?result:CommonResult.fail("证件照合成失败");
    }


    @ApiModelProperty("修改material_user表")
    @PostMapping(value = "/updateMaterialById")
    public CommonResult updateMaterialById(MaterialUser materialUser){
        if(materialUser==null||StringUtils.isBlank(materialUser.getMaterialId())||
                StringUtils.isBlank(materialUser.getUserId())){
            return CommonResult.fail("未包含素材ID、用户ID");
        }
        return materialService.updateMaterialById(materialUser)?CommonResult.success("修改成功"):CommonResult.fail("修改失败");
    }


    @ApiModelProperty("多条件分页查询自己上传的素材")
    @PostMapping(value = "/querySelfMaterialPageListByConditions")
    public CommonResult querySelfMaterialPageListByConditions(User user){
        if(user==null||StringUtils.isBlank(user.getUserId())){
            return CommonResult.fail("未传入用户ID");
        }
        return CommonResult.success(materialService.querySelfMaterialPageListByConditions(user));
    }

    @ApiModelProperty("分页查询采集员上传的公共素材")
    @PostMapping(value = "/queryCollectorMaterialPageList")
    public CommonResult queryCollectorMaterialPageList(User user){
        return CommonResult.success(materialService.queryCollectorMaterialPageList(user));
    }


    @ApiModelProperty("将公共素材转移到我的素材库")
    @PostMapping(value = "/transferMaterials")
    public CommonResult transferMaterials(MaterialUser materialUser){
        if(materialUser==null||StringUtils.isBlank(materialUser.getUserId())||
                StringUtils.isBlank(materialUser.getMaterialId())){
            return CommonResult.fail("未包含素材ID、用户ID");
        }
        long count = materialUserService.count(new QueryWrapper<MaterialUser>()
                .eq("user_id", materialUser.getUserId())
                .eq("material_id", materialUser.getMaterialId()));
        if(count>=1){
            return CommonResult.fail("素材重复添加");
        }
        return materialService.transferMaterials(materialUser)?CommonResult.success("添加成功"):CommonResult.fail("添加失败");
    }


    @ApiModelProperty("文件下载")
    @PostMapping(value = "/downLoadMaterial")
    public void downLoadMaterial(@RequestParam("materialName")String materialName,
                                         HttpServletResponse response) throws IOException {
        String fileName=materialName.split("/")[materialName.split("/").length-1];
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        OSSUtils.downLoadFile(materialName,response.getOutputStream());
    }


    @ApiOperation("管理员获取所有用户素材")
    @PostMapping(value = "/queryAllUserMaterial")
    public CommonResult queryAllUserMaterial(Material material){
        return CommonResult.success(materialService.queryAllUserMaterial(material));
    }

    @ApiOperation("管理员获取公共素材库")
    @PostMapping(value = "/queryAllPublicMaterial")
    public CommonResult queryAllPublicMaterial(Material material){
        return CommonResult.success(materialService.queryAllPublicMaterial(material));
    }


    @ApiOperation("素材合成")
    @PostMapping(value = "/materialFusion")
    public CommonResult materialFusion(@RequestBody Material material){
        if(material==null|| Objects.isNull(material.getImgUrlList())||
                StringUtils.isBlank(material.getBackImgUrl())||StringUtils.isBlank(material.getUserId())||
                StringUtils.isBlank(material.getGroupId())||StringUtils.isBlank(material.getMaterialName())){
            return CommonResult.fail("素材列表、背景图、用户ID、工作组ID、素材名称不全");
        }
        JSONArray images = new JSONArray();
        for(MaterialImg materialImg:material.getImgUrlList()){
            CommonResult bodySeg = imageService.bodySeg(materialImg.getImgUrl());
            if(!bodySeg.getSuccess()){
                return CommonResult.fail("背景分割失败");
            }
            JSONObject object= new JSONObject((Map<String,Object>)bodySeg.getResult());
            object.put("maskImg",object.get("maskUrl"));
            object.put("foregroundImg",object.get("foregroundUrl"));
            object.remove("maskUrl");
            object.remove("foregroundUrl");
            ArrayList<Integer> offset = new ArrayList<>();
            offset.add(materialImg.getOffsetX());
            offset.add(materialImg.getOffsetY());
            object.put("offset",offset);
            ArrayList<Float> scale = new ArrayList<>();
            scale.add(materialImg.getScaleW());
            scale.add(materialImg.getScaleH());
            object.put("scale",scale);
            images.add(object);
        }
        CommonResult fusionResult = imageService.materialFusion(images.toJSONString(), material.getBackImgUrl());
        if(!fusionResult.getSuccess()){
            return CommonResult.fail("素材合成失败");
        }
        JSONObject materialUrls= new JSONObject();
        materialUrls.put("originUrl",fusionResult.getResult());
        material.setMaterialUrls(materialUrls.toJSONString());
        return materialService.materialFusion(material)?fusionResult:CommonResult.fail("素材合成失败");
    }

    @ApiOperation("相框合成")
    @PostMapping(value = "/imgFrameMerge")
    public CommonResult imgFrameMerge(Material material,
                                      @RequestParam("imgUrl")String imgUrl,
                                      @RequestParam("bold")Integer bold,
                                      @RequestParam("type")Integer type){
        if(material==null|| StringUtils.isBlank(material.getUserId())){
            return CommonResult.fail("未包含用户ID，素材名称参数");
        }
        CommonResult mergeResult = imageService.imgFrameMerge(imgUrl, bold, type);
        if(!mergeResult.getSuccess()){
            return CommonResult.fail("相框合成失败");
        }
        JSONObject object = new JSONObject();
        object.put("originUrl",mergeResult.getResult());
        material.setMaterialUrls(object.toJSONString());
        return materialService.insertMaterial(material)?mergeResult:CommonResult.fail("相框合成失败");
    }

}

