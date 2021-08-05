package com.laychv.pay_mall.service;

import com.github.pagehelper.PageInfo;
import com.laychv.pay_mall.vo.ProductDetailVo;
import com.laychv.pay_mall.vo.ResponseVo;

public interface IProductService {
    ResponseVo<PageInfo> productList(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> productDetail(Integer categoryId);
}
