package com.laychv.pay_mall.service.impl;

import com.laychv.pay_mall.consts.MallConst;
import com.laychv.pay_mall.dao.CategoryMapper;
import com.laychv.pay_mall.pojo.Category;
import com.laychv.pay_mall.service.ICategoryService;
import com.laychv.pay_mall.vo.CategoryVo;
import com.laychv.pay_mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        final List<Category> categories = categoryMapper.selectAll();
        final List<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categories) {
            if (category.getParentId().equals(MallConst.ROOT_PARENT_ID)) {
                final CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category,categoryVo);
                categoryVos.add(categoryVo);
            }
        }
        return ResponseVo.success(categoryVos);
    }
}
