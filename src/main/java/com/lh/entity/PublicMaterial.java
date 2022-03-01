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
 * @since 2022-02-14
 */
@Getter
@Setter
@TableName("public_material")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PublicMaterial对象", description = "")
public class PublicMaterial extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("素材ID")
    @TableId("material_id")
    private String materialId;

    @ApiModelProperty("素材名称")
    @TableField("material_name")
    private String materialName;

    @ApiModelProperty("素材URL")
    @TableField("material_url")
    private String materialUrl;

    @ApiModelProperty("素材描述")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("上传者ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("上传时间")
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("下载量")
    @TableField("download_num")
    private Integer downloadNum;

    @ApiModelProperty("转入量")
    @TableField("transform_num")
    private Integer transformNum;

    @ApiModelProperty("搜索量")
    @TableField("search_num")
    private Integer searchNum;

    @ApiModelProperty("点赞量")
    @TableField("thumbs_num")
    private Integer thumbsNum;





    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty("用户头像")
    @TableField(exist = false)
    private String userImgUrl;

    @ApiModelProperty("排序类型")
    @TableField(exist = false)
    private Integer sortType;

    @ApiModelProperty("排序规则")
    @TableField(exist = false)
    private Integer sortOrder;

    @ApiModelProperty("公共素材ID列表")
    @TableField(exist = false)
    private List<String> ids;


}
