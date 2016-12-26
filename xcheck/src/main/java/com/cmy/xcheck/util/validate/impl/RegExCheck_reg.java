package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * @Author chenjw
 * @Date 2016年12月26日
 */
@Component
public class RegExCheck_reg implements ValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        Pattern checkPattern = Pattern.compile(validateParam.getArgumentsVal());
        Matcher matcher = checkPattern.matcher(validateParam.getMainFieldVal());
        if (matcher.matches()) {
            return XResult.failure(validateParam.getMainFieldName() + "参数不正确");
        }
        return XResult.success();
    }

    @Override
    public String getMethodAttr() {
        return "reg";
    }
}
