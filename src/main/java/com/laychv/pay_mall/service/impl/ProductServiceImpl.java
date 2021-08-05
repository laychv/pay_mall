package com.laychv.pay_mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laychv.pay_mall.dao.ProductMapper;
import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.pojo.Product;
import com.laychv.pay_mall.service.ICategoryService;
import com.laychv.pay_mall.service.IProductService;
import com.laychv.pay_mall.vo.ProductDetailVo;
import com.laychv.pay_mall.vo.ProductVo;
import com.laychv.pay_mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.laychv.pay_mall.enums.ProductStatusEnum.DELETE;
import static com.laychv.pay_mall.enums.ProductStatusEnum.OFF_SALE;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired(required = false)
    private ProductMapper productMapper;

    @Override
    public ResponseVo<PageInfo> productList(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> id = new HashSet<>();

        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId, id);
            id.add(categoryId);
        }

        // 分页
        PageHelper.startPage(pageNum, pageSize);

        // lambda + stream
        List<Product> productList = productMapper.selectByCategoryIdSet(id);
        List<ProductVo> productVoList = productList.stream().map(e -> {
            final ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(e, productVo);
            return productVo;
        }).collect(Collectors.toList());

        final PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(productVoList);

        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> productDetail(Integer categoryId) {
        final Product product = productMapper.selectByPrimaryKey(categoryId);
        if (product.getStatus().equals(OFF_SALE.getCode()) || product.getStatus().equals(DELETE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        final ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);
        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVo.success(null, productDetailVo);
    }
}
