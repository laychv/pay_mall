package com.laychv.pay_mall.controller;

import com.github.pagehelper.PageInfo;
import com.laychv.pay_mall.consts.MallConst;
import com.laychv.pay_mall.form.OrderCreateForm;
import com.laychv.pay_mall.service.IOrderService;
import com.laychv.pay_mall.vo.OrderVo;
import com.laychv.pay_mall.vo.ResponseVo;
import com.laychv.pay_mall.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form,
                                      HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.create(userVo.getId(), form.getShippingId());
    }

    @GetMapping("/list")
    public ResponseVo<PageInfo> list(@RequestParam Integer pageNum, @RequestParam Integer pageSize, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(userVo.getId(), pageNum, pageSize);
    }

    @GetMapping("/detail/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable Long orderNo, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(userVo.getId(), orderNo);
    }

    @PutMapping("/cancel/{orderNo}")
    public ResponseVo cancel(@PathVariable Long orderNo, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(userVo.getId(), orderNo);
    }

}
