package com.jqp.controller;

import com.jqp.bo.ShopcartBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import util.JSONResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车接口", tags = "购物车接口")
@RestController
@RequestMapping("/shopcart")
public class ShopCatController {

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车")
    @PostMapping("/add")
    public JSONResult add(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam String userId,
                          @RequestBody ShopcartBO shopCartBO){
        if(StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("");
        }

        System.out.println(shopCartBO);

        //TODO 前端用户在登录的情况下，会同时在后端同步到redis缓存

        return  JSONResult.ok();
    }


    @ApiOperation(value = "删除购物车商品", notes = "删除购物车商品")
    @PostMapping("/del")
    public JSONResult del(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam String userId,
                          @RequestParam String itemSpecId){
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)){
            return JSONResult.errorMsg("参数不能为空");
        }

        //TODO 用户在页面删除购物车商品，如果此时已经登录，则需要同步删除redis中的购物车商品信息

        return  JSONResult.ok();
    }

}
