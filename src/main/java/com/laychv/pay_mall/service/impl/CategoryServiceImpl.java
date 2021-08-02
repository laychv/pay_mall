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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
                BeanUtils.copyProperties(category, categoryVo);
                categoryVos.add(categoryVo);
            }
        }

        // lamb + stream 效果和for each 一样, 但是代码阅读性较差
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(MallConst.ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());
//        return ResponseVo.success(categoryVoList);

        // 排序
        categoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());

        // 查询子目录
        findSubCategory(categories, categoryVos);

        return ResponseVo.success(categoryVos);
    }

    private void findSubCategory(List<Category> categories, List<CategoryVo> categoryVoList) {
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                if (categoryVo.getId().equals(category.getParentId())) {
                    final CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
            }
            // 二级排序 -> 降序
            subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            // 设置子目录
            categoryVo.setSubCategories(subCategoryVoList);
            // 再次查询子目录
            findSubCategory(categories, subCategoryVoList);
        }
    }

    private CategoryVo category2CategoryVo(Category category) {
        final CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
