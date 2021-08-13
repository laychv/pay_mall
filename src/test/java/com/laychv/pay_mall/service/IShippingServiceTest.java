package com.laychv.pay_mall.service;

import com.github.pagehelper.PageInfo;
import com.laychv.pay_mall.PayMallApplicationTests;
import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.form.ShippingForm;
import com.laychv.pay_mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
//@Transactional
public class IShippingServiceTest extends PayMallApplicationTests {

    @Autowired
    private IShippingService service;

    final private Integer uid =1;
    private ShippingForm form;
    private Integer shippingId;

    @Before
    public void before() {
        final ShippingForm form = new ShippingForm();
        form.setReceiverName("lay");
        form.setReceiverAddress("杭州-滨江");
        form.setReceiverCity("杭州");
        form.setReceiverProvince("浙江");
        form.setReceiverPhone("0571");
        form.setReceiverMobile("13456789090");
        form.setReceiverDistrict("滨江区");
        form.setReceiverZip("031000");
        this.form = form;

//        add();
    }

    @Test
    public void add() {
        ResponseVo<Map<String, Integer>> map = service.add(uid, form);
        log.info("map={}", map);
        this.shippingId = map.getData().get("shippingId");
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), map.getStatus());
    }

    @Test
    public void delete() {
        final ResponseVo delete = service.delete(uid, shippingId);
        log.info("map={}", delete);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), delete.getStatus());
    }

    @Test
    public void update() {
        form.setReceiverName("张佚名");
        final ResponseVo update = service.update(1, 10, form);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),update.getStatus());
    }

    @Test
    public void list() {
        final ResponseVo<PageInfo> list = service.list(1, 1, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),list.getStatus());
    }
}