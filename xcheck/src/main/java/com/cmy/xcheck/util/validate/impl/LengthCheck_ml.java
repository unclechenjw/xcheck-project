package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.AbstractValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 全字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public class LengthCheck_ml extends AbstractValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        if (validateParam.getMainFieldVal().length() <= Integer.parseInt(validateParam.getArgumentsVal())) {
            return XResult.success();
        } else {
            return XResult.failure(getFieldAlias(validateParam) + "的长度必须小于或等于" +
                    validateParam.getArgumentsVal() + "位");
        }
    }

    @Override
    public String getMethodAttr() {
        return "ml";
    }
}
