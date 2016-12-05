package com.cmy.xcheck.config;

import com.cmy.xcheck.support.interceptor.XCheckSpringMVCInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kevin on 16/9/10.
 */
@Configuration
@ComponentScan(
        basePackages = {"com.cmy.xcheck.support",
                "com.cmy.xcheck.util"}
)
public class XCheckConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private XCheckSpringMVCInterceptor xcheckSpringMVCInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(xcheckSpringMVCInterceptor);
    }
}
