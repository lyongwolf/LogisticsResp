package com.ly.service;

import com.ly.pojo.User;

import java.util.List;

/**
 * 用户信息
 */
public interface IUserService {

    /**
     * 根据条件查询用户信息
     * @param user
     * @return
     */
    List<User> query(User user) throws Exception;

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    Integer addUser(User user) throws Exception;

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Integer updateUser(User user) throws Exception;

    /**
     * 根据id删除用户信息
     * @param id
     * @return
     */
    Integer deleteUser(Integer id) throws Exception;
}
