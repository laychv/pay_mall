package com.laychv.pay_mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVo {
    private Boolean selectedAll;
    private BigDecimal cartTotalPrice;
    private Integer cartTotalQuantity;
    private List<CartProductVo> list;
}
