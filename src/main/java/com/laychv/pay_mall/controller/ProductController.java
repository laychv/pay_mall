package com.laychv.pay_mall.controller;

import com.github.pagehelper.PageInfo;
import com.laychv.pay_mall.service.IProductService;
import com.laychv.pay_mall.vo.ProductDetailVo;
import com.laychv.pay_mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired(required = false)
    private IProductService productService;

    @PostMapping("/list")
    public ResponseVo<PageInfo> getProductList(
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        return productService.productList(categoryId, pageNum, pageSize);
    }

    /**
     * Postman
     * http://127.0.0.1:8080/product/detail/26
     *
     * @param productId
     * @return
     */
    @GetMapping("/detail/{productId}")
    public ResponseVo<ProductDetailVo> getProductDetail(@PathVariable Integer productId) {
        return productService.productDetail(productId);
    }

    @PostMapping("/detail")
    public ResponseVo<ProductDetailVo> getProductDetail2(@RequestParam("productId") Integer productId) {
        return productService.productDetail(productId);
    }
}
