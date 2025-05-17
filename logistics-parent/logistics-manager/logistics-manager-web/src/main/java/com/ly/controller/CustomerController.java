package com.ly.controller;

import com.ly.dto.CustomerDto;
import com.ly.pojo.Customer;
import com.ly.service.ICustomerService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @RequiresRoles(value = {"业务员", "操作员", "管理员"}, logical = Logical.OR)
    @RequestMapping("/query")
    public String query(Customer customer, Model model) throws Exception {
        List<CustomerDto> list = customerService.query(customer);
        model.addAttribute("list", list);
        return "customer/customer";
    }

    @RequestMapping("/customerDispatch")
    public String customerDispatch(Integer id, Model model) throws Exception {
        customerService.getUpdateInfo(id, model);
        return "customer/updateCustomer";
    }

    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Customer customer) throws Exception {
        if (customer != null && customer.getCustomerId() != null) {
            // 是更新
            customerService.update(customer);
        } else {
            customerService.save(customer);
        }
        return "redirect:/customer/query";
    }

    @RequestMapping("/checkCustomer")
    @ResponseBody
    public Integer checkCustomer(Integer id) {
        return customerService.checkCustomer(id);
    }

    @RequestMapping("/deleteById")
    public String deleteById(Integer id) {
        customerService.deleteById(id);
        return "redirect:/customer/query";
    }

}
