package com.jqp.service.impl;

import com.jqp.bo.SubmitOrderBO;
import com.jqp.pojo.Orders;
import com.jqp.pojo.UserAddress;
import com.jqp.service.AddressService;
import com.jqp.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createOrder(SubmitOrderBO submitOrderBO) {

         String userId=submitOrderBO.getUserId();
         String itemSpecIds=submitOrderBO.getItemSpecIds();
         String addressId=submitOrderBO.getAddressId();
         Integer payMethod=submitOrderBO.getPayMethod();
        UserAddress userAddress=addressService.queryUserAddres(userId, addressId);
         String leftMsg=submitOrderBO.getLeftMsg();
         Orders orders=new Orders();
         orders.setId(sid.nextShort());
         orders.setUserId(userId);

         //todo 生成订单
        // orders.s

    }
}
