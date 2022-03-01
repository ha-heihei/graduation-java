package com.lh.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.lh.entity.Material;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.entity.MaterialUser;
import com.lh.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
public interface IMaterialService extends IService<Material> {

    Boolean insertMaterial(Material material);

    Boolean deleteMaterialById(Material material);

    Boolean updateMaterialById(MaterialUser materialUser);

    Page<Material> querySelfMaterialPageListByConditions(User user);

    Page<Material> queryCollectorMaterialPageList(User user);

    Boolean transferMaterials(MaterialUser materialUser);

}