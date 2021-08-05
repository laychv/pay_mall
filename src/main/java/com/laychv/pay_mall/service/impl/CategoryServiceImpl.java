package com.laychv.pay_mall.service.impl;

import com.laychv.pay_mall.consts.MallConst;
import com.laychv.pay_mall.dao.CategoryMapper;
import com.laychv.pay_mall.pojo.Category;
import com.laychv.pay_mall.service.ICategoryService;
import com.laychv.pay_mall.utils.CategoryUtils;
import com.laychv.pay_mall.vo.CategoryVo;
import com.laychv.pay_mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
//        List<CategoryVo> categoryVoList = categories.stream()
//                .filter(e -> e.getParentId().equals(MallConst.ROOT_PARENT_ID))
//                .map(CategoryUtils::category2CategoryVo)
//                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
//                .collect(Collectors.toList());
//        return ResponseVo.success(categoryVoList);

        // 排序
        categoryVos.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
        // 查询子目录
        CategoryUtils.findSubCategory(categories, categoryVos);
        return ResponseVo.success(categoryVos);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> subId) {
        final List<Category> categories = categoryMapper.selectAll();
        // 注意: 多次查询数据库问题
        getSub(categories, id, subId);
    }

    private void getSub(List<Category> categories, Integer id, Set<Integer> subId) {
        for (Category category : categories) {
            if (category.getParentId().equals(id)) {
                subId.add(category.getId());
                findSubCategoryId(category.getId(), subId);
            }
        }
    }
}
