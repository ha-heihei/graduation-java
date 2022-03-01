package com.lh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lh.entity.GroupMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lihao
 * @since 2022-01-19
 */
@Mapper
public interface GroupMessageMapper extends BaseMapper<GroupMessage> {

    List<GroupMessage> querySelfMessage(@Param(Constants.WRAPPER)QueryWrapper<GroupMessage> wrapper);



}
