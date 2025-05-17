package com.ly.service;

import com.ly.dto.OrderDto;
import org.springframework.ui.Model;

public interface IOrderService {

    /**
     * 进入添加界面前 我们需要准备的数据
     * 1. 查询出所有的业务员
     * 2. 查询出所有的客户
     * 3. 查询基础数据
     *          付款方式
     *          货运方式
     *          取件方式
     *          常用区间（国家/城市）
     *          单位
     * @param model
     */
    void queryAddRequiredData(Model model) throws Exception;


    void  saveOrder(OrderDto dto) throws Exception;

}
