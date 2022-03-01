package com.lh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
@TableName("user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "User对象", description = "")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableId("user_id")
    private String userId;

    @ApiModelProperty("用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("用户密码")
    @TableField(value = "user_pwd")
    private String userPwd;

    @ApiModelProperty("用户头像,默认头像")
    @TableField("user_img_url")
    private String userImgUrl = "https://images.nowcoder.com/images/20211115/633872610_1636955847464/FECD76F09C4EFFA7102ECDBC1795FB3B?x-oss-process=image/resize,m_mfit,h_100,w_100";

    @ApiModelProperty("1为男，2为女")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty("电话")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("1为普通用户、2为采集员、3为管理员")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty("1为可用、0为不可用")
    @TableField("user_status")
    private Integer userStatus;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    @ApiModelProperty("验证码")
    @TableField(exist = false)
    private String code;

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

    @ApiModelProperty("需要删除的用户ID列表")
    @TableField(exist = false)
    private List<String> ids;

    @ApiModelProperty("素材名称")
    @TableField(exist = false)
    private String materialName;

    @ApiModelProperty("用户自己的素材")
    @TableField(exist = false)
    private List<Material> selfMaterials;

    @ApiModelProperty("工作组内用户自己的素材")
    @TableField(exist = false)
    private List<GroupMaterial> groupSelfMaterials;

    @ApiModelProperty("个人素材数量")
    @TableField(exist = false)
    private Integer selfMaterialsNum;

    @ApiModelProperty("工作组素材数量")
    @TableField(exist = false)
    private Integer groupSelfMaterialNum;

    @ApiModelProperty("个人创建工作组数量")
    @TableField(exist = false)
    private Integer selfCreateGroupNum;

    @ApiModelProperty("个人加入工作组数量")
    @TableField(exist = false)
    private Integer selfJoinGroupNum;

    @ApiModelProperty("素材统计")
    @TableField(exist = false)
    private Map<String, List<Integer>> materialStatistics;

    @ApiModelProperty("工作组统计")
    @TableField(exist = false)
    private Map<String, List<Integer>> groupStatistics;

    @ApiModelProperty("获取统计日期列表")
    @TableField(exist = false)
    private List<String> dateList;

    public Boolean infoIsEmpty() {
        return StringUtils.isBlank(this.userName) || StringUtils.isBlank(this.email) ||
                StringUtils.isBlank(this.mobile) || Objects.isNull(this.gender);
    }


}
