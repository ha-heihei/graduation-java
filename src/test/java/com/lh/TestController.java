package com.lh;

import com.lh.common.CommonResult;
import com.lh.service.ImageService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import javax.annotation.Resource;

/**
 * @author LiHao
 * @create 2022-01-17 8:44
 */
@SpringBootTest
public class TestController {

    @Resource
    private ImageService imageService;

    @PostMapping(value = "/faceBeautify")
    public CommonResult test(@RequestParam("imgUrl")String imgUrl){
        return imageService.faceBeautify(imgUrl);
    }

    @PostMapping(value = "/definitionEnhance")
    public CommonResult test1(@RequestPart("imgUrl")String imgUrl){
        return imageService.definitionEnhance(imgUrl);
    }

    @PostMapping(value = "/bodySeg")
    public CommonResult test2(@RequestPart("imgUrl")String imgUrl){
        return imageService.bodySeg(imgUrl);
    }

    @PostMapping(value = "/backGaussianBlur")
    public CommonResult test3(@RequestPart("backImg")String backImg,
                              @RequestPart("maskImg")String maskImg,
                              @RequestPart("foregroundImg")String foregroundImg){
        return imageService.backGaussianBlur(backImg,maskImg,foregroundImg);
    }

    @PostMapping(value = "/backFill")
    public CommonResult test4(@RequestPart("foregroundImg")String foregroundImg,
                              @RequestPart("maskImg")String maskImg,
                              @RequestPart("color")String color){
        return imageService.backFill(foregroundImg,maskImg,color);
    }


}
