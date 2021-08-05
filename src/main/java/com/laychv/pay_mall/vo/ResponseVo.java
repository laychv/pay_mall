package com.laychv.pay_mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.laychv.pay_mall.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {
    private Integer status;
    private String msg;
    private T data;

    private ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    private ResponseVo(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseVo<T> success(String msg, T data) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), msg == null ? ResponseEnum.SUCCESS.getMsg() : msg, data);
    }

    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> ResponseVo<T> success() {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseVo<T> error(ResponseEnum en) {
        return new ResponseVo<>(en.getCode(), en.getMsg());
    }

    public static <T> ResponseVo<T> error(ResponseEnum en, String msg) {
        return new ResponseVo<>(en.getCode(), msg);
    }

    public static <T> ResponseVo<T> error(ResponseEnum en, BindingResult bind) {
        return new ResponseVo<>(en.getCode(),
                Objects.requireNonNull(bind.getFieldError()).getField()
                        + " " + bind.getFieldError().getDefaultMessage());
    }

}
