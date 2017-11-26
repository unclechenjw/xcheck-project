package com.c.j.w.xcheck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kevin on 16/9/10.
 */
@Configuration
@ComponentScan(
        basePackages = {"com.c.j.w.xcheck.support",
                "com.c.j.w.xcheck.util"}
)
public class XCheckConfiguration {
//    @Autowired
//    private XCheckSpringMVCInterceptor xcheckSpringMVCInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(xcheckSpringMVCInterceptor);
//    }
}
