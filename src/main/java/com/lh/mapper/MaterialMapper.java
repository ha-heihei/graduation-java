package com.lh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.Group;
import com.lh.entity.Material;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.entity.PublicMaterial;
import com.lh.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
@Mapper
public interface MaterialMapper extends BaseMapper<Material> {

    Page<Material> querySelfMaterialPageListByConditions(Page<User> page, @Param("ew")QueryWrapper<User> wrapper);

    Page<Material> queryCollectorMaterialPageList(Page<Material> page, @Param("ew")QueryWrapper<Material> wrapper);

    List<Group> querySelfMaterialWhereGroup(@Param("userId")String userId,
                                            @Param("materialId")String materialId);

    List<Material> queryAllUserMaterial(@Param(Constants.WRAPPER)QueryWrapper<Material> wrapper);

    List<PublicMaterial> queryAllPublicMaterial(@Param(Constants.WRAPPER)QueryWrapper<PublicMaterial> wrapper);
}
