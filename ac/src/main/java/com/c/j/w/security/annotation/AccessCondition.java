package com.c.j.w.security.annotation;

import com.c.j.w.security.LimitMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Description: 指定时间内请求次数限制
 * seconds:30, limit:5; 默认30秒内5次请求
 * module: 模块访问限制，参数为模块ID，任意填写，作用于该模块访问次数限制，默认为全局限制
 *         指某个IP或手机号码,在系统中访问频率只能为seconds与limit组合限制
 *         如某个模块限制则填写模块名
 * limitMethod:限制方式：IP访问限制、手机号码访问限制（手机号限制时需要设置telFieldName属性）
 * fieldNameOfTel: 手机号码字段名称
 * @author chenjw
 * @date 2016年12月6日 下午11:20:08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessCondition {

    /** 默认过期时间30秒 */
    int seconds() default 30;

    /** 请求次数限制 */
    int limit() default 5;

    /** 模块访问限制，参数为模块ID，任意填写，作用于该模块访问次数限制，
     * 默认为全局限制 */
    String module() default "";

    /** 限制方式：IP访问限制、手机号码访问限制（手机号限制时需要设置telFieldName属性）*/
    LimitMethod limitMethod() default LimitMethod.IP;

    /** 手机号码字段名称 */
    String fieldNameOfTel() default "";

}
