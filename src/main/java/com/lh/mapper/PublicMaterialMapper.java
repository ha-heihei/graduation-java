package com.lh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.PublicMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lihao
 * @since 2022-02-14
 */
@Mapper
public interface PublicMaterialMapper extends BaseMapper<PublicMaterial> {

    Page<PublicMaterial> queryCollectorMaterialsPageList(Page<PublicMaterial> page,
                                                         @Param("userId")String userId,
                                                         @Param(Constants.WRAPPER)QueryWrapper<PublicMaterial> wrapper);


}
