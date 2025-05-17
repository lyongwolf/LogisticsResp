package com.ly.controller;

import com.ly.dto.OrderDto;
import com.ly.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/orderDispatch")
    public String orderDispatch(Model model) throws Exception {
        orderService.queryAddRequiredData(model);
        return "order/addOrder";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveOrder(@RequestBody OrderDto dto) throws Exception {
        orderService.saveOrder(dto);
        return "success";
    }
}
