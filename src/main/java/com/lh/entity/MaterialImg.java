package com.lh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LiHao
 * @create 2022-03-04 13:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialImg {

    private String imgUrl;
    private Integer offsetX;
    private Integer offsetY;

    private Float scaleW;
    private Float scaleH;

}
