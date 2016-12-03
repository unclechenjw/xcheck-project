package com.cmy.xcheck.support.annotation;

import com.cmy.xcheck.config.XCheckConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by kevin on 16/9/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(XCheckConfiguration.class)
public @interface EnableXCheck {
}
