package com.laychv.pay_mall;

import com.laychv.pay_mall.dao.OrderMapper;
import com.laychv.pay_mall.pojo.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayMallApplicationTests {

    @Autowired(required = false)
    private OrderMapper orderMapper;

    @Test
    public void getOrder() {
        final Order order = orderMapper.selectByPrimaryKey(1);
        System.out.println(order.toString());
    }

}
