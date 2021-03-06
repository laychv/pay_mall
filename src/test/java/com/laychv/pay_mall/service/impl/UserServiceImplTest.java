package com.laychv.pay_mall.service.impl;

import com.laychv.pay_mall.PayMallApplicationTests;
import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.enums.RoleEnum;
import com.laychv.pay_mall.pojo.User;
import com.laychv.pay_mall.service.IUserService;
import com.laychv.pay_mall.vo.ResponseVo;
import com.laychv.pay_mall.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
public class UserServiceImplTest extends PayMallApplicationTests {

    public static final String USERNAME = "jack";
    public static final String PASSWORD = "123456";

    @Autowired
    private IUserService userService;

    @Test
    public void register() {
        User user = new User(USERNAME, PASSWORD, "jack@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }

    @Test
    public void login() {
        ResponseVo<UserVo> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void getUser() {
        final ResponseVo<List<UserVo>> user = userService.info();
        log.info(user.toString());
    }
}