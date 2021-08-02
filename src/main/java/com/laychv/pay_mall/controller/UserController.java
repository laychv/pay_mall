package com.laychv.pay_mall.controller;

import com.laychv.pay_mall.consts.MallConst;
import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.form.UserLoginForm;
import com.laychv.pay_mall.form.UserRegisterForm;
import com.laychv.pay_mall.pojo.User;
import com.laychv.pay_mall.service.IUserService;
import com.laychv.pay_mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 请求参数写法区别?
 * 第一种: 写一行
 *
 * @PostMapping(value = "/user/register")
 * 第二种: 写两行
 * @RequestMapping("/user") <p>
 * <p>
 * public class UserController{}
 * <p>
 * @PostMapping(value = "/register")
 * <p>
 * public ResponseVo<User> register(){}
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 注册
     *
     * @param form
     * @param result
     * @return
     */
    //    @PostMapping(value = "/user/register",produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, result);
        }
        User user = new User();
        BeanUtils.copyProperties(form, user);
        log.info("username={}", user);
        return userService.register(user);
    }

    @PostMapping("/register2")
    public ResponseVo<User> register(@Valid @RequestParam("username") @NotNull(message = "用户名不能为空") String username,
                                     @Valid @RequestParam("password") @NotNull(message = "密码不能为空") String password,
                                     @Valid @RequestParam("email") String email,
                                     @Valid @RequestParam("role") @NotEmpty(message = "角色不能为空") Integer role) {
        final User user = new User(username, password, email, role);
        BeanUtils.copyProperties(username, user);
        log.info("username={}", user);
        return userService.register(user);
    }

    /**
     * 登陆
     *
     * @param form
     * @param session
     * @param result
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm form, HttpSession session, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, result);
        }
        final ResponseVo<User> login = userService.login(form.getUsername(), form.getPassword());
        // 设置session
        session.setAttribute(MallConst.CURRENT_USER, login.getData());
        log.info("/user/login sessionId={}" + session.getId());
        // todo 登陆之后, 返回给前端的数据需要裁剪
        return login;
    }

    /**
     * 用户信息
     * session保存在内存中, 重启的时候会丢失数据, 数据库或者Radius存储
     * 改进: token + radius
     *
     * @param session
     * @return
     */
    @GetMapping("/getUser")
    @ResponseBody
    public ResponseVo<User> userInfo(HttpSession session) {
        // 获取session
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        log.info("/user sessionId={}", session.getId());
        if (user == null) {
            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }
        return ResponseVo.success(user);
    }

    /**
     * 用户列表
     */
    @GetMapping("/getUsers")
    public ResponseVo<List<User>> getUsers() {
        return userService.getUser();
    }

    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public ResponseVo<String> logout(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }
        // 移除session
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }
}
