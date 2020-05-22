package com.jqp.service.impl;

import com.jqp.bo.UserBO;
import com.jqp.mapper.UsersMapper;
import com.jqp.pojo.Users;
import com.jqp.service.UserService;
import enums.Sex;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.entity.Example;
import util.DateUtil;
import util.MD5Utils;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    /**
     * 判断用户名是否已经存在
     * @param username
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        Users users = usersMapper.selectOneByExample(userExample);
        return users == null ? false : true;
    }

    /**
     * 注册
     * @param userBO
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users insertUser(UserBO userBO) {
        Users users = new Users();
        String userId = sid.nextShort();
        users.setId(userId);
        users.setUsername(userBO.getUsername());
        String pwd = MD5Utils.getMD5Str(userBO.getPassword());
        users.setPassword(pwd);
        // 默认生日
        users.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 默认用户昵称同用户名
        users.setNickname(userBO.getUsername());
        // 默认头像
        users.setFace(USER_FACE);
        // 默认性别为 保密
        users.setSex(Sex.secret.type);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        usersMapper.insert(users);
        return users;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        userCriteria.andEqualTo("password", MD5Utils.getMD5Str(password));
        Users users = usersMapper.selectOneByExample(userExample);
        return users;
    }
}
