package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.AbstractValidateMethod;
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
public class MoneyCheck_$ extends AbstractValidateMethod {
    private static final Pattern MONEY_FORMAT_PATTERN =
            Pattern.compile("^-?\\d+(\\.\\d+)?$");
    @Override
    public XResult validate(ValidateParam validateParam) {
        Matcher matcher = MONEY_FORMAT_PATTERN.matcher(validateParam.getMainFieldVal());
        if (matcher.matches()) {
            return XResult.failure(getFieldAlias(validateParam) + "金额格式不正确");
        }
        return XResult.success();
    }

    @Override
    public String getMethodAttr() {
        return "$";
    }
}
