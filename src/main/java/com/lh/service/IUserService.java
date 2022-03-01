package com.lh.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihao
 * @since 2022-01-15
 */
public interface IUserService extends IService<User> {

    Boolean insertUserInfo(User user);

    User checkLogin(User user);

    Page<User> queryPageUserInfoByConditions(User user);

    User statistics(User user);

}
