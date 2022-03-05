package com.lh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lh.entity.ViewMark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lihao
 * @since 2022-03-05
 */
@Mapper
public interface ViewMarkMapper extends BaseMapper<ViewMark> {

    IPage<ViewMark> queryViewMarkByArea(IPage<ViewMark> page,
                                        @Param(Constants.WRAPPER)QueryWrapper<ViewMark> wrapper);

}
