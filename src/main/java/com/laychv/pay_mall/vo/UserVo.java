package com.laychv.pay_mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserVo {

    private Integer id;

    private String username;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;
}
