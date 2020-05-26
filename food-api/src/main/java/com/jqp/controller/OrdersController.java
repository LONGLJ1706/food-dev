package com.jqp.controller;

import com.jqp.bo.SubmitOrderBO;
import com.jqp.pojo.Orders;
import com.jqp.service.AddressService;
import com.jqp.service.OrderService;
import com.jqp.service.impl.OrderServiceImpl;
import enums.PayMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.IMOOCJSONResult;

import java.util.List;

@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController {

   @Autowired
   private OrderService orderService;

    @ApiOperation(value = "用户新增订单", notes = "用户新增订单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO) {
        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type
                && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type ) {
            return IMOOCJSONResult.errorMsg("支付方式不支持！");
        }
        // 1. 创建订单
        System.out.println(submitOrderBO.toString());
        orderService.createOrder(submitOrderBO);
        //2、创建订单后移除购物车中已提交的商品


        //3、向支付中心发送当前订单，

        return IMOOCJSONResult.ok();
    }

}
