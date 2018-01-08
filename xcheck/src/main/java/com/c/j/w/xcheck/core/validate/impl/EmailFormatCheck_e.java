package com.c.j.w.xcheck.core.validate.impl;

import com.c.j.w.xcheck.core.XResult;
import com.c.j.w.xcheck.core.validate.AbstractValidateMethod;
import com.c.j.w.xcheck.core.validate.ValidateParam;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮箱
 * @Author chenjw
 * @Date 2016年12月26日
 */
@Component("e")
public class EmailFormatCheck_e extends AbstractValidateMethod {

    private static final Pattern Email_Pattern = Pattern.compile("^.+?@.+?\\..+$");

    @Override
    public XResult validate(ValidateParam validateParam) {
        Matcher matcher = Email_Pattern.matcher(validateParam.getMainFieldVal());
        if (matcher.matches()) {
            return XResult.success();
        }
        return XResult.failure(getFieldAlias(validateParam) + "邮箱格式不正确");
    }

}
