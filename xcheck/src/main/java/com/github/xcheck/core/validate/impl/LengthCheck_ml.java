package com.github.xcheck.core.validate.impl;

import com.github.xcheck.core.XResult;
import com.github.xcheck.core.validate.AbstractValidateMethod;
import com.github.xcheck.core.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 全字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component("ml")
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

}
