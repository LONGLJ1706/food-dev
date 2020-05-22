package com.jqp.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户业务对象", description = "由用户传入的对象")
public class UserBO {

    @ApiModelProperty(value = "用户名", required = true, example = "jqp")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;

    @ApiModelProperty(value = "确认密码", required = false, example = "123456")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
