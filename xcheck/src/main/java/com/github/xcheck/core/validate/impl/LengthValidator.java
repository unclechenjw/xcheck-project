package com.github.xcheck.core.validate.impl;

import com.github.xcheck.core.XResult;
import com.github.xcheck.core.validate.AbstractValidateMethod;
import com.github.xcheck.core.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 长度交易
 * @Author chenjw
 * @Date 2016年12月26日
 */
@Component("l")
public class LengthValidator extends AbstractValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        String[] args = validateParam.getArgumentsVal().split(",");
        if (args.length == 1) {
            if (validateParam.getMainFieldVal().length() != Integer.parseInt(validateParam.getArgumentsVal())) {
                return XResult.failure(getFieldAlias(validateParam) + "必须为" + validateParam.getArgumentsVal() + "位");
            }
        } else {
            boolean inLengthRange = validateParam.getMainFieldVal().length() >= Integer.parseInt(args[0]) &&
                    validateParam.getMainFieldVal().length() <= Integer.parseInt(args[1]);
            if (!inLengthRange) {
                return XResult.failure(getFieldAlias(validateParam) + "必须" + args[0] + "至" + args[1] + "位");
            }
        }
        return XResult.success();
    }

}
