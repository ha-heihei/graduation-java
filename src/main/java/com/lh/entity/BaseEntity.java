package com.lh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author LiHao
 * @create 20220115 17:38
 */
@Data
public class BaseEntity {

    @TableField(exist = false)
    private Integer page=-1;

    @TableField(exist = false)
    private Integer limit=-1;

}
