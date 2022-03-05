package com.lh.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lh.common.CommonResult;
import com.lh.entity.ViewMark;
import com.lh.service.IViewMarkService;
import com.lh.utils.OSSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lihao
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/viewMark")
public class ViewMarkController {

    @Resource
    private IViewMarkService viewMarkService;

    @ApiOperation("添加打卡信息")
    @PostMapping(value = "/insertViewMarkInfo")
    public CommonResult insertViewMarkInfo(ViewMark viewMark,
                                           @RequestPart(value = "viewImg",required = false)MultipartFile viewImg) throws IOException {
        if(viewMark==null|| StringUtils.isBlank(viewMark.getUserId())||
                Objects.isNull(viewMark.getLat())||Objects.isNull(viewMark.getLng())||
                StringUtils.isBlank(viewMark.getViewName())){
            return CommonResult.fail("未完全包含用户ID，经纬度，打卡名称等参数");
        }
        if(viewImg!=null&&!viewImg.isEmpty()){
            viewMark.setViewImgUrl(OSSUtils.uploadFile(viewImg.getInputStream()));
        }
        return viewMarkService.insertViewMarkInfo(viewMark)?CommonResult.success("添加成功"):CommonResult.fail("添加失败");
    }

    @ApiOperation("根据地点查询附近打卡信息")
    @PostMapping(value = "/queryViewMarkByArea")
    public CommonResult queryViewMarkByArea(ViewMark viewMark){
        return CommonResult.success(viewMarkService.queryViewMarkByArea(viewMark));
    }



}

