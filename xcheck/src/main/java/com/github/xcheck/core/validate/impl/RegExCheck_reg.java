package com.github.xcheck.core.validate.impl;

import com.github.xcheck.core.validate.ValidateParam;
import com.github.xcheck.core.XResult;
import com.github.xcheck.core.validate.AbstractValidateMethod;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * @Author chenjw
 * @Date 2016年12月26日
 */
@Component("reg")
public class RegExCheck_reg extends AbstractValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        Pattern checkPattern = Pattern.compile(validateParam.getArgumentsVal());
        Matcher matcher = checkPattern.matcher(validateParam.getMainFieldVal());
        if (matcher.matches()) {
            return XResult.success();
        }
        return XResult.failure(getFieldAlias(validateParam) + "参数不正确");
    }

}
