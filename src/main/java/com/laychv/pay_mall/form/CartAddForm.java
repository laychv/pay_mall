package com.laychv.pay_mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartAddForm {
    @NotNull
    private Integer productId;

    private Boolean selected = true;
}