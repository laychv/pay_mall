package com.laychv.pay_mall;

import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.enums.RoleEnum;
import com.laychv.pay_mall.pojo.User;
import com.laychv.pay_mall.service.IUserService;
import com.laychv.pay_mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 廖师兄
 */
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
        ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void getUser() {
        final ResponseVo<List<User>> user = userService.getUser();
        log.info(user.toString());
    }
}