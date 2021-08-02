package com.laychv.pay_mall.service.impl;

import com.laychv.pay_mall.PayMallApplicationTests;
import com.laychv.pay_mall.dao.CategoryMapper;
import com.laychv.pay_mall.pojo.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
public class CategoryServiceImplTest extends PayMallApplicationTests {

    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    @Test
    public void selectAll() {
        final List<Category> categories = categoryMapper.selectAll();
        System.out.println(categories.toString());
    }
}