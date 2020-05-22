package com.jqp.controller;

import com.jqp.bo.SubmitOrderBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import util.IMOOCJSONResult;

import java.util.List;

@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController {



    @ApiOperation(value = "用户新增订单", notes = "用户新增订单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO) {

        //1、创建订单
        //2、创建订单后移除购物车中已提交的商品
        //3、向支付中心发送当前订单，
          System.out.println(submitOrderBO.toString());
        return IMOOCJSONResult.ok();
    }

}
