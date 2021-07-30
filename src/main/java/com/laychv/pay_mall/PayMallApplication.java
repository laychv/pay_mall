package com.laychv.pay_mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
@MapperScan(basePackages = "com.laychv.pay_mall.dao")
//@MapperScans({
//        @MapperScan(basePackages = "com.laychv.pay_mall.dao"),
//        @MapperScan(basePackages = "")})
public class PayMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayMallApplication.class, args);
    }

}
