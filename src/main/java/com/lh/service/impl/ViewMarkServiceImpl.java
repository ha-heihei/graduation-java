package com.lh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.ViewMark;
import com.lh.mapper.ViewMarkMapper;
import com.lh.service.IViewMarkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lihao
 * @since 2022-03-05
 */
@Service
public class ViewMarkServiceImpl extends ServiceImpl<ViewMarkMapper, ViewMark> implements IViewMarkService {

    @Resource
    private ViewMarkMapper viewMarkMapper;


    @Override
    public Boolean insertViewMarkInfo(ViewMark viewMark) {
        viewMark.setViewId(String.valueOf(System.currentTimeMillis()));
        viewMark.setCreateTime(LocalDateTime.now());
        viewMark.setScanNum(0);
        if(StringUtils.isBlank(viewMark.getViewRemarks())){
            viewMark.setViewRemarks("未描述");
        }
        return viewMarkMapper.insert(viewMark)==1;
    }

    @Override
    public IPage<ViewMark> queryViewMarkByArea(ViewMark viewMark) {
        QueryWrapper<ViewMark> queryWrapper = new QueryWrapper<ViewMark>()
                .like(StringUtils.isNotBlank(viewMark.getProvince()), "v.province", viewMark.getProvince())
                .like(StringUtils.isNotBlank(viewMark.getCity()), "v.city", viewMark.getCity())
                .like(StringUtils.isNotBlank(viewMark.getDistrict()), "v.district", viewMark.getDistrict())
                .le(Objects.nonNull(viewMark.getEndTime()), "v.create_time", viewMark.getEndTime())
                .ge(Objects.nonNull(viewMark.getBeginTime()), "v.create_time", viewMark.getBeginTime())
                .like(StringUtils.isNotBlank(viewMark.getViewName()), "v.view_name", viewMark.getViewName());
        Page<ViewMark> page = new Page<>(viewMark.getPage(), viewMark.getLimit());
        return viewMarkMapper.queryViewMarkByArea(page,queryWrapper);
    }
}
