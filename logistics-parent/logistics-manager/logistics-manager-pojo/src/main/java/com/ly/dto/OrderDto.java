package com.ly.dto;

import com.ly.pojo.Order;
import com.ly.pojo.OrderDetail;

import java.util.List;

public class OrderDto extends Order {

    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
