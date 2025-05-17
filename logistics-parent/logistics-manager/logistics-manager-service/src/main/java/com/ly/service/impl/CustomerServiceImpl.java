package com.ly.service.impl;

import com.ly.common.Constant;
import com.ly.dto.CustomerDto;
import com.ly.mapper.CustomerMapper;
import com.ly.mapper.OrderMapper;
import com.ly.pojo.*;
import com.ly.service.IBasicService;
import com.ly.service.ICustomerService;
import com.ly.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBasicService basicService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void getUpdateInfo(Integer id, Model model) throws Exception {
        // 查询角色是 业务员的用户信息
        List<User> users = userService.queryUserByRoleName(Constant.ROLE_SALEMAN);

        // 查询 货运区间 基础数据
        List<BasicData> intervals = basicService.queryByParentName(Constant.BASIC_COMMON_INTERVAL);

        if (id != null && id > 0) {
            // 表示是更新数据  根据当前的编号查询出对应的客户信息
            Customer customer = customerMapper.selectByPrimaryKey(id);
            model.addAttribute("customer", customer);
        }

        model.addAttribute("users", users);
        model.addAttribute("intervals", intervals);
    }

    @Override
    public Integer save(Customer customer) throws Exception {
        return customerMapper.insertSelective(customer);
    }

    @Override
    public List<CustomerDto> query(Customer customer) throws Exception {
        // 1. 根据当前登录用户获取角色信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roles = userService.queryUserHaveRole(user);
        // 2. 判断当前用户是否具有操作员或者管理员的角色
        boolean flag = false;
        for (Role role : roles) {
            if (Constant.ROLE_ADMIN.equals(role.getRoleName()) || Constant.ROLE_OPERATOR.equals(role.getRoleName())) {
                flag = true;
                break;
            }
        }
        CustomerExample example = new CustomerExample();
        if (!flag) {
            // 说明只有业务员这一个角色
            example.createCriteria().andUserIdEqualTo(user.getUserId());
        }
        List<Customer> list = customerMapper.selectByExample(example);

        // 返回结果是 Dto
        List<CustomerDto> dtos = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Customer c : list) {
                CustomerDto dto = new CustomerDto();
                // 实现对象属性的拷贝
                BeanUtils.copyProperties(c, dto);

                // 扩展 Dto 中的属性 业务员， 常用区间名称
                String userName = userService.queryById(dto.getUserId()).getUserName();
                dto.setUserName(userName);

                String intervalName = basicService.queryById(dto.getBaseId()).getBaseName();
                dto.setIntervalName(intervalName);

                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public void update(Customer customer) {
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public Integer checkCustomer(Integer id) {
        OrderExample example = new OrderExample();
        example.createCriteria().andCustomerIdEqualTo(id);
        int i = orderMapper.countByExample(example);
        return i > 0 ? 1 : 0;
    }

    @Override
    public void deleteById(Integer id) {
        customerMapper.deleteByPrimaryKey(id);
    }
}
