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
@TableName("material_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MaterialUser对象", description = "")
public class MaterialUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("素材ID")
    @TableField("material_id")
    private String materialId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("素材名称")
    @TableField("material_name")
    private String materialName;

    @ApiModelProperty("素材备注")
    @TableField("remarks")
    private String remarks;

}
