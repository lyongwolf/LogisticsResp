package com.ly.service.impl;

import com.ly.common.Constant;
import com.ly.dto.CustomerDto;
import com.ly.dto.OrderDto;
import com.ly.mapper.OrderDetailMapper;
import com.ly.mapper.OrderMapper;
import com.ly.pojo.BasicData;
import com.ly.pojo.OrderDetail;
import com.ly.pojo.User;
import com.ly.service.IBasicService;
import com.ly.service.ICustomerService;
import com.ly.service.IOrderService;
import com.ly.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBasicService basicService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public void queryAddRequiredData(Model model) throws Exception {
        // 1.查询所有的客户
        List<CustomerDto> customers = customerService.query(null);
        // 2.查询所有的业务员
        List<User> users = userService.queryUserByRoleName(Constant.ROLE_SALEMAN);
        // 3.查询基础数据
        // 3.1 付款方式
        List<BasicData> payments = basicService.queryByParentName(Constant.BASIC_COMMON_PAYMENT_TYPE);
        // 3.2 货运方式
        List<BasicData> freights = basicService.queryByParentName(Constant.BASIC_FREIGHT_TYPE);
        // 3.3 取件方式
        List<BasicData> fetches = basicService.queryByParentName(Constant.BASIC_FETCH_TYPE);
        // 3.4 国家/城市
        List<BasicData> countries = basicService.queryByParentName(Constant.BASIC_COMMON_INTERVAL);
        // 3.5 单位
        List<BasicData> units = basicService.queryByParentName(Constant.BASIC_UNIT);

        model.addAttribute("customers", customers);
        model.addAttribute("users", users);
        model.addAttribute("payments", payments);
        model.addAttribute("freights", freights);
        model.addAttribute("fetches", fetches);
        model.addAttribute("countries", countries);
        model.addAttribute("units", units);
    }

    @Override
    public void saveOrder(OrderDto dto) throws Exception {
        // 保存主表数据
        orderMapper.insertSelective(dto);
        // 保存详情数据
        List<OrderDetail> orderDetails = dto.getOrderDetails();
        if (orderDetails != null && orderDetails.size() > 0) {
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setOrderId(dto.getOrderId());
                orderDetailMapper.insertSelective(orderDetail);
            }
        }
    }
}
