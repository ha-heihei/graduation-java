package com.lh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("group_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GroupUser对象", description = "")
public class GroupUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("工作组ID")
    @TableField("group_id")
    private String groupId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;





    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty("用户头像,默认头像")
    @TableField(exist = false)
    private String userImgUrl="https://images.nowcoder.com/images/20211115/633872610_1636955847464/FECD76F09C4EFFA7102ECDBC1795FB3B?x-oss-process=image/resize,m_mfit,h_100,w_100";

    @ApiModelProperty("1为男，2为女")
    @TableField(exist = false)
    private Integer gender;

    @ApiModelProperty("电话")
    @TableField(exist = false)
    private String mobile;

    @ApiModelProperty("邮箱")
    @TableField(exist = false)
    private String email;


}
