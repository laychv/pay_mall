package com.laychv.pay_mall.interceptor;

import com.laychv.pay_mall.consts.MallConst;
import com.laychv.pay_mall.exception.UserLoginRuntimeException;
import com.laychv.pay_mall.pojo.User;
import com.laychv.pay_mall.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器处理结果
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    /**
     * true  继续
     * false 终止
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserVo user = (UserVo) request.getSession().getAttribute(MallConst.CURRENT_USER);
        log.info("user={}", user);
        if (user == null) {
//            response.getWriter().print("这种方式不是json格式, 需要手动转成json格式, 返回前端显示");
            // 通过异常的方式, 返回json给前端, 统一解析
            throw new UserLoginRuntimeException();
        }
        return true;
    }
}
