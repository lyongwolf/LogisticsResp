package com.ly.service;

import com.ly.pojo.Role;

import java.util.List;

/**
 * 角色信息
 */
public interface IRoleService {

    /**
     * 根据条件查询角色信息
     * @param role
     * @return
     */
    List<Role> query(Role role) throws Exception;

    /**
     * 根据编号查询角色信息
     * @param id
     * @return
     * @throws Exception
     */
    Role queryById(Integer id) throws Exception;

    /**
     * 添加角色信息
     * @param role
     * @return
     */
    Integer addRole(Role role) throws Exception;

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    Integer updateRole(Role role) throws Exception;

    /**
     * 根据编号删除角色信息
     * @param id
     * @return
     */
    Integer deleteRole(Integer id) throws Exception;
}
