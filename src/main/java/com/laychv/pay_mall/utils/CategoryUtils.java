package com.laychv.pay_mall.utils;

import com.laychv.pay_mall.pojo.Category;
import com.laychv.pay_mall.vo.CategoryVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoryUtils {
    public static void findSubCategory(List<Category> categories, List<CategoryVo> categoryVoList) {
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

    public static CategoryVo category2CategoryVo(Category category) {
        final CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
