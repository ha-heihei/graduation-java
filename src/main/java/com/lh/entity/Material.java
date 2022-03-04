package com.lh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lh.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("material")
@ApiModel(value = "Material对象", description = "")
public class Material extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("素材ID")
    @TableId("material_id")
    private String materialId;

    @ApiModelProperty("素材名称")
    @TableField("material_name")
    private String materialName;

    @ApiModelProperty("存放json,包含原图、mask、前景图")
    @TableField("material_urls")
    private String materialUrls;

    @ApiModelProperty("上传者id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;





    @ApiModelProperty("素材备注")
    @TableField(exist = false)
    private String remarks;

    @ApiModelProperty("用户名称")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty("用户头像")
    @TableField(exist = false)
    private String userImgUrl;

    @ApiModelProperty("素材所在工作组")
    @TableField(exist = false)
    private List<Group> groupList;

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

    @ApiModelProperty("工作组id")
    @TableField(exist = false)
    private String groupId;

    @ApiModelProperty("合成素材-素材列表")
    @TableField(exist = false)
    private List<MaterialImg> imgUrlList;

    @ApiModelProperty("合成素材-背景图")
    @TableField(exist = false)
    private String backImgUrl;



}
