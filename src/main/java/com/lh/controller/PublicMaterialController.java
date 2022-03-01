package com.lh.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lh.common.CommonResult;
import com.lh.entity.PublicMaterial;
import com.lh.mapper.PublicMaterialMapper;
import com.lh.service.IPublicMaterialService;
import com.lh.utils.OSSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lihao
 * @since 2022-02-14
 */
@RestController
@RequestMapping("/publicMaterial")
public class PublicMaterialController {

    @Resource
    private IPublicMaterialService publicMaterialService;


    @ApiOperation("上传素材")
    @PostMapping(value = "/insertMaterial")
    public CommonResult insertMaterial(PublicMaterial material,
                                       @RequestPart(value = "material",required = false)MultipartFile materialFile) throws IOException {
        if(material==null||StringUtils.isBlank(material.getMaterialName())||
                StringUtils.isBlank(material.getUserId())){
            return CommonResult.fail("未传入素材名称、用户ID");
        }
        if(materialFile==null||materialFile.isEmpty()){
            return CommonResult.fail("未上传素材");
        }
        material.setMaterialUrl(OSSUtils.uploadFile(materialFile.getInputStream()));
        return publicMaterialService.insertMaterial(material)?CommonResult.success("上传成功"):CommonResult.fail("上传失败");
    }


    @ApiOperation("获取采集员的上传素材，如果是采集员,则userId无效,普通用户有效")
    @PostMapping(value = "/queryCollectorMaterialsPageList")
    public CommonResult queryCollectorMaterialsPageList(PublicMaterial publicMaterial){
        if(publicMaterial==null|| StringUtils.isBlank(publicMaterial.getUserId())||
                Objects.isNull(publicMaterial.getSortOrder())||Objects.isNull(publicMaterial.getSortType())){
            return CommonResult.fail("未传入用户ID、排序类型、排序规则");
        }
        return CommonResult.success(publicMaterialService.queryCollectorMaterialsPageList(publicMaterial));
    }

    @ApiOperation("删除一个素材")
    @PostMapping(value = "/deleteMaterialById")
    public CommonResult deleteMaterialById(PublicMaterial material){
        if(material==null||StringUtils.isBlank(material.getMaterialId())){
            return CommonResult.fail("未传入素材ID");
        }
        return publicMaterialService.removeById(material.getMaterialId())?CommonResult.success("删除成功"):CommonResult.fail("删除失败");
    }

    @ApiOperation("修改素材信息，素材名称、素材描述")
    @PostMapping(value = "/updateMaterialById")
    public CommonResult updateMaterialById(PublicMaterial material){
        if(material==null||StringUtils.isBlank(material.getMaterialId())){
            return CommonResult.fail("未传入素材ID");
        }
        return publicMaterialService.updateById(material)?CommonResult.success("修改成功"):CommonResult.fail("修改失败");
    }

    @ApiOperation("素材下载，下载量加一")
    @PostMapping(value = "/downloadMaterial")
    public CommonResult downloadMaterial(@RequestParam("materialUrl")String materialUrl,
                                 @RequestParam("materialId")String materialId,
                                 HttpServletResponse response) throws IOException {
        if(publicMaterialService.update(new UpdateWrapper<PublicMaterial>()
                .eq("material_id",materialId)
                .setSql("download_num=download_num+1"))){
            String fileName=materialUrl.split("/")[materialUrl.split("/").length-1];
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
            OSSUtils.downLoadFile(materialUrl,response.getOutputStream());
            return CommonResult.success("下载成功");
        }
        return CommonResult.fail("下载请求失败");
    }

    @ApiOperation("转入我的素材，转换格式，转入量加一，material、material_user表都需添加")
    @PostMapping(value = "/transformMaterial")
    public CommonResult transformMaterial(PublicMaterial material){
        if(material==null||StringUtils.isBlank(material.getMaterialId())||
                StringUtils.isBlank(material.getUserId())||StringUtils.isBlank(material.getMaterialName())){
            return CommonResult.fail("未传入素材ID、用户ID、素材名称");
        }
        return publicMaterialService.transformMaterial(material)?CommonResult.success("转入成功"):CommonResult.fail("转入失败");
    }

    @ApiOperation("点赞量加一")
    @PostMapping(value = "/addThumbs")
    public CommonResult addThumbs(PublicMaterial material){
        if(material==null||StringUtils.isBlank(material.getMaterialId())){
            return CommonResult.fail("未传入素材ID");
        }
        UpdateWrapper<PublicMaterial> updateWrapper = new UpdateWrapper<PublicMaterial>()
                .eq("material_id", material.getMaterialId())
                .setSql("thumbs_num=thumbs_num+1");
        return publicMaterialService.update(updateWrapper)?CommonResult.success("点赞成功"):CommonResult.fail("请求失败");
    }


    @ApiOperation("相关推荐")
    @PostMapping(value = "/recommendMaterial")
    public CommonResult recommendMaterial(PublicMaterial material){
        if(material==null|| StringUtils.isBlank(material.getUserId())||
                Objects.isNull(material.getIds())){
            return CommonResult.fail("未传入用户ID、已显示的素材Ids");
        }
        return CommonResult.success(publicMaterialService.recommendMaterial(material));
    }



}

