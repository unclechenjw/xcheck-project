package com.c.j.w.security.annotation;

import com.c.j.w.security.config.XSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @module 安全码校验启动注解
 * @author chenjw
 * @date: 2016/12/6 16:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(XSecurityConfiguration.class)
public @interface EnableSecurity {
}
