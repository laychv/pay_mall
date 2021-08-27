package com.laychv.pay_mall.service;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laychv.pay_mall.PayMallApplicationTests;
import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.form.CartAddForm;
import com.laychv.pay_mall.vo.CartVo;
import com.laychv.pay_mall.vo.OrderVo;
import com.laychv.pay_mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class IOrderServiceImplTest extends PayMallApplicationTests {

    private Integer uid = 1;
    private Integer shippingId = 4;
    private Integer productId = 26;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired(required = false)
    private IOrderService service;

    @Autowired
    private ICartService cartService;

    @Before
    public void add() {
        CartAddForm form = new CartAddForm();
        form.setProductId(productId);
        form.setSelected(true);
        ResponseVo<CartVo> responseVo = cartService.add(uid, form);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void init() {
        final ResponseVo<OrderVo> responseVo = create();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    private ResponseVo<OrderVo> create() {
        final ResponseVo<OrderVo> responseVo = service.create(uid, shippingId);
        log.info("result={}", gson.toJson(responseVo));
        return responseVo;
    }

    @Test
    public void list() {
        final ResponseVo<PageInfo> responseVo = service.list(uid, 1, 1);
        log.info("result={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void detail() {
        ResponseVo<OrderVo> vo = create();
        ResponseVo<OrderVo> responseVo = service.detail(uid, vo.getData().getOrderNo());
        log.info("result={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void cancel() {
        final ResponseVo<OrderVo> vo = create();
        final ResponseVo responseVo = service.cancel(uid, vo.getData().getOrderNo());
        log.info("result={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}