package com.laychv.pay_mall.enums;

import lombok.Getter;

@Getter
public enum PayTypeEnum {

    PAY_ONLINE(1);

    Integer code;

    PayTypeEnum(Integer code) {
        this.code = code;
    }
}
