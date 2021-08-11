package com.laychv.pay_mall.service;

import com.laychv.pay_mall.form.CartAddForm;
import com.laychv.pay_mall.form.CartUpdateForm;
import com.laychv.pay_mall.vo.CartVo;
import com.laychv.pay_mall.vo.ResponseVo;

public interface ICartService {
    ResponseVo<CartVo> add(Integer uid, CartAddForm form);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form);

    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> UnSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);
}
