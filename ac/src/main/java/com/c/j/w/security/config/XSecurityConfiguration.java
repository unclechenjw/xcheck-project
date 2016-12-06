package com.c.j.w.security.config;

import com.c.j.w.security.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @module 扫码配置，拦截器添加
 * @author chenjw
 * @date: 2016/12/6 16:51
 */
@Configuration
@ComponentScan(
        basePackages = {"com.c.j.w.security"}
)
public class XSecurityConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor);
    }
}
