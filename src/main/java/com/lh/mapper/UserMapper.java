package com.lh.mapper;

import com.lh.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
