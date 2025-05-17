package com.ly.service;

import com.ly.dto.CustomerDto;
import com.ly.pojo.Customer;
import org.springframework.ui.Model;

import java.util.List;

public interface ICustomerService {
    /**
     * 获取添加或者更新客户需要准备的数据
     * @param id
     * @param model
     */
    void getUpdateInfo(Integer id, Model model) throws Exception;


    Integer save(Customer customer) throws Exception;

    /**
     * 客户管理只能是 业务员，操作员，管理员
     * 操作员和管理员 可以查看所有的客户信息
     * 业务员只能查看属于自身的客户
     * @param customer
     * @return
     * @throws Exception
     */
    List<CustomerDto> query(Customer customer) throws Exception;

    void update(Customer customer);

    Integer checkCustomer(Integer id);

    void deleteById(Integer id);
}