package com.laychv.pay_mall.service;

import com.laychv.pay_mall.pojo.User;
import com.laychv.pay_mall.vo.ResponseVo;
import com.laychv.pay_mall.vo.UserVo;

import java.util.List;

public interface IUserService {

    ResponseVo<User> register(User user);

    ResponseVo<UserVo> login(String username, String password);

    ResponseVo<List<User>> info();
}
