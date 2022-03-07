package com.lh.service;

import com.alibaba.fastjson.JSONArray;
import com.lh.common.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiHao
 * @create 2022-01-16 0:04
 */
@FeignClient(name = "imageService",url = "http://localhost:5000/")
public interface ImageService {

    /**
     * 人脸美颜API
     * @param imgUrl
     * @return
     */
    @PostMapping(value = "/faceBeautify",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult faceBeautify(@RequestPart("imgUrl") String imgUrl);

    /**
     * 图像增强API
     * @param imgUrl
     * @return
     */
    @PostMapping(value = "/definitionEnhance",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult definitionEnhance(@RequestPart("imgUrl")String imgUrl);

    /**
     * 人体分割API，返回originUrl,maskUrl,foregroundUrl
     * @param imgUrl
     * @return
     */
    @PostMapping(value = "/bodySeg",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult bodySeg(@RequestPart("imgUrl")String imgUrl);

    /**
     * 背景虚化API
     * @param backImg
     * @param maskImg
     * @param foregroundImg
     * @return
     */
    @PostMapping(value = "/backGaussianBlur",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult backGaussianBlur(@RequestPart("backImg")String backImg,
                                  @RequestPart("maskImg")String maskImg,
                                  @RequestPart("foregroundImg")String foregroundImg);

    /**
     * 证件照API，color可以为r,w,b
     * @param foregroundImg
     * @param maskImg
     * @param color
     * @return
     */
    @PostMapping(value = "/backFill",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult backFill(@RequestPart("foregroundImg")String foregroundImg,
                          @RequestPart("maskImg")String maskImg,
                          @RequestPart("color")String color);

    /**
     * 图像锐化API，参数同图像增强API
     * @param imgUrl
     * @return
     */
    @PostMapping(value = "/contrastEnhance",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult contrastEnhance(@RequestPart("imgUrl")String imgUrl);


    /**
     * 素材合成Api，imgUrlList子项包括offset[x,y],foregroundImg,maskImg
     * @param imgUrlList
     * @param backImgUrl
     * @return
     */
    @PostMapping(value = "/materialFusion",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult materialFusion(@RequestPart("imgUrlList")String imgUrlList,
                                @RequestPart("backImgUrl")String backImgUrl);


    @PostMapping(value = "/imgFrameMerge",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult imgFrameMerge(@RequestPart("imgUrl")String imgUrl,
                               @RequestPart("bold")Integer bold,
                               @RequestPart("type")Integer type);

}
