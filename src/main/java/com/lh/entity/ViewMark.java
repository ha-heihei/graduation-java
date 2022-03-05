package com.lh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lh.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author lihao
 * @since 2022-03-05
 */
@Getter
@Setter
@TableName("view_mark")
@ApiModel(value = "ViewMark对象", description = "")
public class ViewMark extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("打卡ID")
    @TableId("view_id")
    private String viewId;

    @ApiModelProperty("打卡名称")
    @TableField("view_name")
    private String viewName;

    @ApiModelProperty("打卡备注")
    @TableField("view_remarks")
    private String viewRemarks;

    @ApiModelProperty("经度")
    @TableField("lat")
    private BigDecimal lat;

    @ApiModelProperty("纬度")
    @TableField("lng")
    private BigDecimal lng;

    @ApiModelProperty("省名称")
    @TableField("province")
    private String province;

    @ApiModelProperty("市名称")
    @TableField("city")
    private String city;

    @ApiModelProperty("区名称")
    @TableField("district")
    private String district;

    @ApiModelProperty("打卡图片")
    @TableField("view_img_url")
    private String viewImgUrl;

    @ApiModelProperty("上传用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("浏览量")
    @TableField("scan_num")
    private Integer scanNum;




    @ApiModelProperty("起始时间")
    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @ApiModelProperty("截止时间")
    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty("创建人名称")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty("创建人头像")
    @TableField(exist = false)
    private String userImgUrl;



}
