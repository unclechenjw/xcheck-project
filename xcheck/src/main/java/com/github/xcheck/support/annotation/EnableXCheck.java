package com.github.xcheck.support.annotation;

import com.github.xcheck.config.XcheckConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author chenjw
 * @date 2020/8/7
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(XcheckConfiguration.class)
public @interface EnableXCheck {
}
