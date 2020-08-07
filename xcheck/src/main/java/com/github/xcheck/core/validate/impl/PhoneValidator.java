package com.github.xcheck.core.validate.impl;

import com.github.xcheck.core.validate.AbstractValidateMethod;
import com.github.xcheck.core.validate.ValidateParam;
import com.github.xcheck.core.XResult;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码
 * @Author chenjw
 * @Date 2016年12月26日
 */
@Component("p")
public class PhoneValidator extends AbstractValidateMethod {
    private static final Pattern Tel_Pattern = Pattern.compile("^\\d{11}$");

    @Override
    public XResult validate(ValidateParam validateParam) {
        // 默认校验11位手机号码,后期可根据参数加入其它校验方法
        Matcher matcher = Tel_Pattern.matcher(validateParam.getMainFieldVal());
        if (matcher.matches()) {
            return XResult.success();
        }
        return XResult.failure("手机号码格式不正确");
    }

}
