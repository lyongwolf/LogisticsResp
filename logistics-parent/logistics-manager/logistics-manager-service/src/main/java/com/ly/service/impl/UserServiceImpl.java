package com.ly.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.ly.dto.UserDto;
import com.ly.mapper.RoleMapper;
import  com.ly.mapper.UserMapper;
import com.ly.mapper.UserRoleMapper;
import com.ly.pojo.*;
import com.ly.service.IUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * 用户管理
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<User> query(User user) throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (user != null) {
            if (user.getUserName() != null && !user.getUserName().equals("")) {
                criteria.andUserNameEqualTo(user.getUserName());
            }
        }

        // 查询的是 u2 不为 1 的记录
        criteria.andU2IsNull();
        return userMapper.selectByExample(example);
    }

    @Override
    public Integer addUser(User user) throws  Exception {
        return userMapper.insertSelective(user);
    }

    @Override
    public Integer updateUser(User user) throws Exception {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer deleteUser(Integer id) throws Exception {
        User user = new User();
        user.setUserId(id);
        user.setU2("1");
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer saveOrUpdate(UserDto dto) throws Exception {
        // 1.添加用户信息
        User user = dto.getUser();

        if (user.getUserId() != null) {
            // 表示是进行用户的更新操作
            this.updateUser(user);

            // 根据用户编号删除对应的角色信息
            UserRoleExample example = new UserRoleExample();
            example.createCriteria().andUserIdEqualTo(user.getUserId());
            userRoleMapper.deleteByExample(example);
        } else {
            // 表示用户添加操作

            // 需要对密码加密 MD5加密 + salt（盐值）
            String salt = UUID.randomUUID().toString();
            Md5Hash passwordHash = new Md5Hash(user.getPassword(), salt, 1024);
            user.setPassword(passwordHash.toString());
            user.setU1(salt);

            this.addUser(user);// 在 sql 语句对应的 mapper.xml文件中开启主键自增后回填
        }

        // 2.分配用户和角色的关联关系
        // 获取分配给当前用户的角色信息
        List<Integer> roleIds = dto.getRoleIds();
        if (roleIds != null && roleIds.size() > 0) {
            // 表示分配角色信息
            for (Integer roleId : roleIds) {
                // 将用户和角色的关联关系保存到 t_user_role 中
                UserRoleKey userRole = new UserRoleKey();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(roleId);
                userRoleMapper.insertSelective(userRole);
            }
        }
        return 1;
    }

    @Override
    public User queryById(Integer userId) throws Exception {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<Integer> queryUserRoleIds(Integer userId) throws Exception {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserRoleKey> userRoleKeys = userRoleMapper.selectByExample(example);
        List<Integer> ids = new ArrayList<>();
        for (UserRoleKey userRoleKey : userRoleKeys) {
            ids.add(userRoleKey.getRoleId());
        }
        return ids;
    }

    @Override
    public PageInfo<User> queryByPage(UserDto userDto) throws Exception {
        PageHelper.startPage(userDto.getPageNum(), userDto.getPageSize());
        List<User> list = this.query(userDto.getUser());
        PageInfo<User> pageInfo = new PageInfo<>(list);
        for (User u : list) {
            System.out.println(new Gson().toJson(u));
        }
        return pageInfo;
    }

    @Override
    public User login(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUserNameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Role> queryUserHaveRole(User user) throws Exception {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(user.getUserId());
        // 查询出用户具有的角色的所有的编号
        List<UserRoleKey> roleIds = userRoleMapper.selectByExample(example);
        if (roleIds != null && roleIds.size() > 0) {
            List<Role> roles = new ArrayList<>();
            for (UserRoleKey userRoleKey : roleIds) {
                Role role = roleMapper.selectByPrimaryKey(userRoleKey.getRoleId());
                roles.add(role);
            }
            return roles;
        }
        return null;
    }

    @Override
    public List<User> queryUserByRoleName(String roleName) throws Exception {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleNameEqualTo(roleName);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if (roles != null && roles.size() > 0) {
            Role role = roles.get(0);
            UserRoleExample userRoleExample = new UserRoleExample();
            userRoleExample.createCriteria().andRoleIdEqualTo(role.getRoleId());
            List<UserRoleKey> userRoleKeys = userRoleMapper.selectByExample(userRoleExample);
            if (userRoleKeys != null && userRoleKeys.size() > 0) {
                List<User> list = new ArrayList<>();
                HashSet<Integer> set = new HashSet<>();
                for (UserRoleKey userRoleKey : userRoleKeys) {
                    Integer id = userRoleKey.getUserId();
                    if (!set.contains(id)) {
                        User user = this.queryById(id);
                        list.add(user);
                        set.add(id);
                    }
                }
                return list;
            }
        }
        return null;
    }
}
