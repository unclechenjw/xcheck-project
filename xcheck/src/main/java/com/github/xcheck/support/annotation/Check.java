package com.github.xcheck.support.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*********************************************************
 * 校验规则
 * 参数格式 【 字段名@验证方法缩写名_参数】
 * [filed1,field2]@d@ml(10)
 * 如验证字段是否全字母 ：Field@w
 * 验证字段长度1至3位： Field@l(1,3) 最大长度10位Field@ML_10
 * @w:word      是否全字母
 * @W:non-word  是否全非字母
 * @d:digit     是否全数字
 * @D:non-digit 是否全非数字
 * @l(1): length 长度是否为1位
 * @l(1,5):     长度是否在指定范围内1至5位
 * @ml(5): max_length 长度小于等于5位
 * @e:Email     是否邮箱地址格式
 * @p:phone     是否11位有效手机号码
 * @$: money    金额格式正负数 小数位不限
 * @in:         字段必须参数范围内  example： Field@IN_a,b,c Field只能为a,b或c
 * @if          example if('conditon1','conditon2','conditon3')
 * @reg regEx   正则表达式校验
 * @id(countryCode) 校验身份证号码，无参默认中国ID
 * : 校验公式冒号后':'可以添加错误提示内容,无则使用系统默认提示
 *
 * ###########################################
 * 字段可空或适应某校验规则请替换@为#调用校验方法
 * 注意！ 以上所有@校验方法 ，#全部支持
 * 例如：验证字段为空或者全字母 ：Field#w
 * #w:     字符为空或者字母
 * #ML(10)  字符为空或者最大长度为10位
 *
 * 数值或日期字段之间逻辑比较运算
 * 例如： >、>=、<、<=、==与!=
 * example1: field1<field2 字段1小于字段2
 * example2: field<100     字段小于指定值100
 * @see: 例子见底部Main方法
 *
 */
@Target({java.lang.annotation.ElementType.METHOD,
        java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Check {

    /** 校验规则 */
    String[] value() default "";

    /**
     * 字段别名，设置为field=字段，多个用小写逗号分隔[,]
     * 当校验不通过时，提示字段使用别名，否则默认校验字段 */
    String[] fieldAlias() default "";


}
