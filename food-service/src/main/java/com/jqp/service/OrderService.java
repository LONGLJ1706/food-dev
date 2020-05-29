package com.jqp.service;

import com.jqp.bo.SubmitOrderBO;
import com.jqp.vo.OrderVO;

public interface OrderService {
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

}
