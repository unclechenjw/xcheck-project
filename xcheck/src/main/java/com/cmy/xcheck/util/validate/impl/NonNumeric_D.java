package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 非字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public class NonNumeric_D implements ValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        return null;
    }

    @Override
    public String getMethodAttr() {
        return "D";
    }
}
