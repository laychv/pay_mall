package com.laychv.pay_mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.laychv.pay_mall.PayMallApplicationTests;
import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.service.IProductService;
import com.laychv.pay_mall.vo.ProductDetailVo;
import com.laychv.pay_mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImplTest extends PayMallApplicationTests {

    @Autowired
    private IProductService productMapper;

    /**
     * 分页 无效
     */
    @Test
    public void productList() {
        final ResponseVo<PageInfo> list = productMapper.productList(null, 1, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), list.getStatus());
    }

    @Test
    public void productDetail() {
        final ResponseVo<ProductDetailVo> detail = productMapper.productDetail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), detail.getStatus());
    }
}