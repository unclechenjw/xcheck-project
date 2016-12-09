package com.cmy.xcheck.util.validate;

import com.cmy.xcheck.support.XResult;
import org.springframework.stereotype.Component;

/**
 * 校验方法接口
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public interface ValidateMethod {

    XResult validate(String... args);

    String getMethodAttr();
}
