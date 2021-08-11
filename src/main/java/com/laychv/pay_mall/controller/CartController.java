package com.laychv.pay_mall.controller;

import com.laychv.pay_mall.consts.MallConst;
import com.laychv.pay_mall.form.CartAddForm;
import com.laychv.pay_mall.form.CartUpdateForm;
import com.laychv.pay_mall.service.ICartService;
import com.laychv.pay_mall.vo.CartVo;
import com.laychv.pay_mall.vo.ResponseVo;
import com.laychv.pay_mall.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
//@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @PostMapping("/cart/add")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm form, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(), form);
    }

    @GetMapping("/cart/list")
    public ResponseVo<CartVo> list(HttpSession session) {
        UserVo user = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PutMapping("/cart/update/{productId}")
    public ResponseVo<CartVo> update(
            @PathVariable Integer productId,
            @Valid @RequestBody CartUpdateForm form,
            HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(userVo.getId(), productId, form);
    }

    @DeleteMapping("/cart/delete{productId}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(userVo.getId(), productId);
    }

    @PutMapping("/cart/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(userVo.getId());
    }

    @PutMapping("/cart/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.UnSelectAll(userVo.getId());
    }

    @GetMapping("/cart/sum")
    public ResponseVo<Integer> sum(HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(userVo.getId());
    }

}
