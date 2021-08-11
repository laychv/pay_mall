package com.laychv.pay_mall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laychv.pay_mall.PayMallApplicationTests;
import com.laychv.pay_mall.form.CartAddForm;
import com.laychv.pay_mall.form.CartUpdateForm;
import com.laychv.pay_mall.vo.CartVo;
import com.laychv.pay_mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ICartServiceTest extends PayMallApplicationTests {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    private ICartService cartService;

    @Test
    public void add() {
        final CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(28);
        cartAddForm.setSelected(true);
        final ResponseVo<CartVo> add = cartService.add(1, cartAddForm);
        log.info("list={}", gson.toJson(add));
    }

    @Test
    public void list() {
        final ResponseVo<CartVo> list = cartService.list(1);
        log.info("list={}" + gson.toJson(list));
    }

    @Test
    public void update() {
        final CartUpdateForm form = new CartUpdateForm();
        form.setQuantity(3);
        form.setSelected(false);
        cartService.update(1, 26, form);
    }

    @Test
    public void delete() {
        cartService.delete(1, 28);
    }
}