package com.cmy.xcheck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kevin on 16/9/10.
 */
@Configuration
@ComponentScan(
        basePackages = {"com.cmy.xcheck.support",
                "com.cmy.xcheck.util"}
)
public class XCheckConfiguration {

}
