package com.laychv.pay_mall.exception;

import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常 统一处理
 */
@ControllerAdvice
public class RuntimeExceptionHandler {

    /**
     * 统一异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVo<?> handleException(RuntimeException e) {
        return ResponseVo.error(ResponseEnum.ERROR, e.getMessage());
    }

    /**
     * 统一拦截器处理
     * 自定义拦截器类
     * {@link UserLoginRuntimeException}
     *
     * @return
     */
    @ExceptionHandler(UserLoginRuntimeException.class)
    @ResponseBody
    public ResponseVo<?> userLoginHandle() {
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }

}