package com.laychv.pay_mall.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final static String ERR = "/error";
    private final static String UL = "/user/login";
    private final static String UR = "/user/register";
    private final static String CL = "/category/list";
    private final static String PL = "/product/list";
    private final static String PD = "/product/detail/*";
    private final static String CA = "/cart/*";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/**")// 拦截所有
                .excludePathPatterns(ERR, UL, UR, CL, PL, PD, CA);//排除拦截
    }
}