package com.laychv.pay_mall.controller;

import com.laychv.pay_mall.consts.MallConst;
import com.laychv.pay_mall.form.ShippingForm;
import com.laychv.pay_mall.service.IShippingService;
import com.laychv.pay_mall.vo.ResponseVo;
import com.laychv.pay_mall.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private IShippingService service;

    @PostMapping("/add")
    public ResponseVo add(@Valid @RequestBody ShippingForm form, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return service.add(userVo.getId(), form);
    }

    @DeleteMapping("/delete/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return service.delete(userVo.getId(), shippingId);
    }

    @PutMapping("/update/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId, @Valid @RequestBody ShippingForm form, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return service.update(userVo.getId(), shippingId, form);
    }

    @GetMapping("/list")
    public ResponseVo list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute(MallConst.CURRENT_USER);
        return service.list(userVo.getId(), pageNum, pageSize);
    }
}
