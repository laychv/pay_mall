package com.laychv.pay_mall.service;

import com.laychv.pay_mall.vo.CategoryVo;
import com.laychv.pay_mall.vo.ResponseVo;

import java.util.List;

public interface ICategoryService {
    ResponseVo<List<CategoryVo>> selectAll();
}
