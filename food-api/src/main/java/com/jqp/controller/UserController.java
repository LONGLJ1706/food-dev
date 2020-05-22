package com.jqp.controller;

import com.jqp.bo.UserBO;
import com.jqp.pojo.Users;
import com.jqp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.CookieUtils;
import util.JSONResult;
import util.JsonUtils;
import util.MD5Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@Api(value = "用户接口", tags = "用于登录注册的相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    @ApiOperation(value = "用户名是否存在")
    public JSONResult usernameIsExist(@RequestParam String username){
        if(StringUtils.isBlank(username)){
            return JSONResult.errorMsg("参数为空");
        }
        Boolean result = userService.queryUsernameIsExist(username);
        if(result){
            return JSONResult.errorMsg("用户名已经存在");
        }

        return JSONResult.ok();
    }


    @PostMapping("/regist")
    @ApiOperation(value = "用户注册")
    public JSONResult inserUser(@RequestBody UserBO userBO, HttpServletRequest request,
                                HttpServletResponse response){
        //判断参数是否为空
        if(StringUtils.isBlank(userBO.getUsername())){
            return JSONResult.errorMsg("用户名不能为空");
        }
        if(StringUtils.isBlank(userBO.getPassword())){
            return JSONResult.errorMsg("密码不能为空");
        }
        if(StringUtils.isBlank(userBO.getConfirmPassword())){
            return JSONResult.errorMsg("确认密码不能为空");
        }

        //验证用户名是否存在
        Boolean result = userService.queryUsernameIsExist(userBO.getUsername());
        if(result){
            return JSONResult.errorMsg("用户名已经存在");
        }

        //验证密码和确认密码是否一致
        String pwd = MD5Utils.getMD5Str(userBO.getPassword());
        String confirePwd = MD5Utils.getMD5Str(userBO.getConfirmPassword());
        if(!pwd.equals(confirePwd)){
            return JSONResult.errorMsg("密码和确认密码不一致");
        }

        if(userBO.getPassword().length() < 6){
            return JSONResult.errorMsg("密码长度不能小于6");
        }

        Users users = userService.insertUser(userBO);

        users = setUserInfo(users);

        //设置cookie, isEncode=true 表示加密
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);

        //TODO 生成用户token，存入redis会话
        //TODO 同步购物车数据

        return JSONResult.ok(users);
    }


    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public JSONResult login(@RequestBody UserBO userBO, HttpServletRequest request,
                            HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return JSONResult.errorMsg("参数为空");
        }
        Users result = userService.queryLogin(username, password);
        if(result == null){
            return JSONResult.errorMsg("用户名或密码不正确");
        }

        result = setUserInfo(result);

        //设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(result), true);

        //TODO 生成用户token，存入redis会话
        //TODO 同步购物车数据


        return JSONResult.ok(result);
    }


    private Users setUserInfo(Users users){
        users.setPassword(null);
        users.setEmail(null);
        users.setRealname(null);
        users.setCreatedTime(null);
        users.setUpdatedTime(null);
        return users;
    }


    @PostMapping("/logout")
    @ApiOperation(value = "用户登出")
    public JSONResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response){
        //清除用户相关的cookie
        CookieUtils.deleteCookie(request, response, "user");
        return JSONResult.ok();
    }

}
