package com.github.xcheck.support.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenjw
 * @date 2020/8/7
 **/
@Target({java.lang.annotation.ElementType.METHOD,
        java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Check {

    /**
     * 校验规则
     */
    String[] value() default "";

    /**
     * 字段别名，设置为field=字段，多个用小写逗号分隔[,]
     * 当校验不通过时，提示字段使用别名，否则默认校验字段
     */
    String[] fieldAlias() default "";


}
