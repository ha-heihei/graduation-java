package com.lh.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.PublicMaterial;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihao
 * @since 2022-02-14
 */
public interface IPublicMaterialService extends IService<PublicMaterial> {

    Page<PublicMaterial> queryCollectorMaterialsPageList(PublicMaterial publicMaterial);

    Boolean insertMaterial(PublicMaterial material);

    Boolean transformMaterial(PublicMaterial material);

    Page<PublicMaterial> recommendMaterial(PublicMaterial material);

}
