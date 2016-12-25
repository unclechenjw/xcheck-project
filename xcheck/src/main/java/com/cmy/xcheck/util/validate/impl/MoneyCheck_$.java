package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public class MoneyCheck_$ implements ValidateMethod {
    private static final Pattern MONEY_FORMAT_PATTERN =
            Pattern.compile("^-?\\d+(\\.\\d{1,2})?$");
    @Override
    public XResult validate(ValidateParam validateParam) {
        Matcher matcher = MONEY_FORMAT_PATTERN.matcher(validateParam.getMainFieldVal());
        if (matcher.find()) {
            return XResult.failure(validateParam.getMainFieldName() + "金额格式不正确");
        } else {
            return XResult.success();
        }

    }

    @Override
    public String getMethodAttr() {
        return "$";
    }
}
