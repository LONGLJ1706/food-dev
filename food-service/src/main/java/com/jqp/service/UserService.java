package com.jqp.service;

import com.jqp.bo.UserBO;
import com.jqp.pojo.Users;

public interface UserService {
    Boolean queryUsernameIsExist(String username);
    Users insertUser(UserBO userBO);
    Users queryLogin(String username, String password);
}
