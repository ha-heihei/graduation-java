package com.lh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lh.entity.ViewMark;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihao
 * @since 2022-03-05
 */
public interface IViewMarkService extends IService<ViewMark> {

    Boolean insertViewMarkInfo(ViewMark viewMark);

    IPage<ViewMark> queryViewMarkByArea(ViewMark viewMark);

}
