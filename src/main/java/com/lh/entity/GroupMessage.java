package com.lh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lh.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;

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
 * @since 2022-01-19
 */
@Getter
@Setter
@TableName("group_message")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GroupMessage对象", description = "")
public class GroupMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("消息ID")
    @TableId("message_id")
    private String messageId;

    @ApiModelProperty("发起方ID")
    @TableField("originator_id")
    private String originatorId;

    @ApiModelProperty("接收方ID")
    @TableField("receiver_id")
    private String receiverId;

    @ApiModelProperty("邀请加入工作组ID")
    @TableField("group_id")
    private String groupId;

    @ApiModelProperty("邀请备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("消息状态，1为待验证，2为同意，3为拒绝")
    @TableField("message_status")
    private Integer messageStatus;

    @ApiModelProperty("消息类型")
    @TableField(exist = false)
    private String messageType;




    @ApiModelProperty("邀请者信息")
    @TableField(exist = false)
    private User originator;

    @ApiModelProperty("受邀者信息")
    @TableField(exist = false)
    private User receiver;

    @ApiModelProperty("邀请者名称")
    @TableField(exist = false)
    private String originatorName;

    @ApiModelProperty("受邀者名称")
    @TableField(exist = false)
    private String receiverName;

    @ApiModelProperty("邀请者头像")
    @TableField(exist = false)
    private String originatorImgUrl;

    @ApiModelProperty("受邀者头像")
    @TableField(exist = false)
    private String receiverImgUrl;



    @ApiModelProperty("用户ID")
    @TableField(exist = false)
    private String userId;

    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty("用户头像,默认头像")
    @TableField(exist = false)
    private String userImgUrl="https://images.nowcoder.com/images/20211115/633872610_1636955847464/FECD76F09C4EFFA7102ECDBC1795FB3B?x-oss-process=image/resize,m_mfit,h_100,w_100";

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

    @ApiModelProperty("创建时间")
    @TableField(exist = false)
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

    @ApiModelProperty("工作组消息查询时间")
    @TableField(exist = false)
    private String groupMsgTime;


}
