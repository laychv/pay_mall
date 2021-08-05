package com.laychv.pay_mall.service.impl;

import com.laychv.pay_mall.dao.UserMapper;
import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.enums.RoleEnum;
import com.laychv.pay_mall.pojo.User;
import com.laychv.pay_mall.service.IUserService;
import com.laychv.pay_mall.vo.ResponseVo;
import com.laychv.pay_mall.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {
        final int count = userMapper.countByUsername(user.getUsername());
        if (count > 0) {
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        final int email = userMapper.countByEmail(user.getEmail());
        if (email > 0) {
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }
        user.setRole(RoleEnum.CUSTOMER.getCode());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        final int result = userMapper.insertSelective(user);
        if (result == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        System.out.println(count);
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<UserVo> login(String username, String password) {
        final User user = userMapper.selectByUsername(username);
        if (user == null) {
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        if (!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        final UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return ResponseVo.success(userVo);
    }

    @Override
    public ResponseVo<List<User>> info() {
        final List<User> users = userMapper.getUser();
        final List<User> userList = new ArrayList<>();
        for (User user : users) {
            final User user1 = new User();
            BeanUtils.copyProperties(user, user1);
            userList.add(user);
        }
        return ResponseVo.success(userList);
    }
}
