package com.lh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

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
@TableName("group_material")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GroupMaterial对象", description = "")
public class GroupMaterial extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("工作组ID")
    @TableField("group_id")
    private String groupId;

    @ApiModelProperty("素材ID")
    @TableField("material_id")
    private String materialId;

    @ApiModelProperty("1代表材料、2代表成品")
    @TableField("group_material_type")
    private Integer groupMaterialType;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("上传者ID")
    @TableField("user_id")
    private String userId;




    @ApiModelProperty("素材名称")
    @TableField(exist = false)
    private String materialName;

    @ApiModelProperty("存放json,包含原图、mask、前景图")
    @TableField(exist = false)
    private String materialUrls;

    @ApiModelProperty("删除素材的主键")
    @TableField(exist = false)
    private List<String> ids;



    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty("用户头像,默认头像")
    @TableField(exist = false)
    private String userImgUrl;

    @ApiModelProperty("1为男，2为女")
    @TableField(exist = false)
    private Integer gender;




    @ApiModelProperty("工作组名称")
    @TableField(exist = false)
    private String groupName;

    @ApiModelProperty("工作组头像")
    @TableField(exist = false)
    private String groupImgUrl;

    @ApiModelProperty("工作组描述")
    @TableField(exist = false)
    private String groupDescription;


}
