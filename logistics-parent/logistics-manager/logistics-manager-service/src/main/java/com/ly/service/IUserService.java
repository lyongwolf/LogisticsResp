package com.ly.service;

import com.github.pagehelper.PageInfo;
import com.ly.dto.UserDto;
import com.ly.pojo.Role;
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
     * 根据编号删除用户信息
     * @param id
     * @return
     */
    Integer deleteUser(Integer id) throws Exception;

    /**
     * 添加用户和更新用户信息
     * @param dto
     * @return
     * @throws Exception
     */
    Integer saveOrUpdate(UserDto dto) throws Exception;

    User queryById(Integer userId) throws Exception;

    List<Integer> queryUserRoleIds(Integer userId) throws Exception;

    /**
     * 分页查询
     * @return
     * @param userDto
     */
    PageInfo<User> queryByPage(UserDto userDto) throws Exception;

    /**
     * 完成登录认证的方法
     * @param username
     * @return
     */
    User login(String username);

    /**
     * 查询用户角色信息
     * userId --> List<roleId> --> List<Role>
     * @param user
     * @return
     */
    List<Role> queryUserHaveRole(User user) throws Exception;

    /**
     * 根据角色名称查询用户信息 t_role --> t_user_role --> userId --> User
     * @param roleName
     * @return
     */
    List<User> queryUserByRoleName(String roleName) throws Exception;
}
