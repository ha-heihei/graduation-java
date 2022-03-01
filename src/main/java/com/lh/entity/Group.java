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
@TableName("work_group")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Group对象", description = "")
public class Group extends BaseEntity {

    public static final String DEFAULT_IMAGE_URL="https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-19/groupAvatar.jpg";

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("工作组ID")
    @TableId("group_id")
    private String groupId;

    @ApiModelProperty("工作组名称")
    @TableField("group_name")
    private String groupName;

    @ApiModelProperty("创建者id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("工作组头像")
    @TableField("group_img_url")
    private String groupImgUrl;

    @ApiModelProperty("工作组描述")
    @TableField("group_description")
    private String groupDescription;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;





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

    @ApiModelProperty("手机号")
    @TableField(exist = false)
    private String mobile;

    @ApiModelProperty("创建人头像")
    @TableField(exist = false)
    private String userImgUrl;

    @ApiModelProperty("群人数")
    @TableField(exist = false)
    private Integer peopleNum;

    @ApiModelProperty("素材数量")
    @TableField(exist = false)
    private Integer materialNum;

    @ApiModelProperty("群员信息")
    @TableField(exist = false)
    private List<User> groupUsers;

    @ApiModelProperty("素材信息")
    @TableField(exist = false)
    private List<GroupMaterial> groupMaterials;

    @ApiModelProperty("是否加入该群组")
    @TableField(exist = false)
    private Integer joinStatus;

    @ApiModelProperty("加入信息")
    @TableField(exist = false)
    private String joinMsg;


}
