package com.ly.service.impl;

import com.ly.mapper.UserMapper;
import com.ly.pojo.User;
import com.ly.pojo.UserExample;
import com.ly.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户管理
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> query(User user) throws Exception {
        UserExample userExample = new UserExample();
        return mapper.selectByExample(userExample);
    }

    @Override
    public Integer addUser(User user) throws Exception {
        return mapper.insertSelective(user);
    }

    @Override
    public Integer updateUser(User user) throws Exception {
        return mapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer deleteUser(Integer id) throws Exception {
        return mapper.deleteByPrimaryKey(id);
    }
}
