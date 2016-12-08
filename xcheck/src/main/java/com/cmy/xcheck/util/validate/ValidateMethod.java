package com.cmy.xcheck.util.validate;

import com.cmy.xcheck.support.XResult;
import org.springframework.stereotype.Component;

/**
 * 杭州动享互联网技术有限公司
 * 校验方法接口
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public interface ValidateMethod {

    XResult validate(String... args);
}
