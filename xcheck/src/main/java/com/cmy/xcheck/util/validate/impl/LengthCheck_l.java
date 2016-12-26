package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 全字母
 * @Author chenjw
 * @Date 2016年12月26日
 */
@Component
public class LengthCheck_l implements ValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        String[] args = validateParam.getArgumentsVal().split(",");
        if (args.length == 1) {
            if (validateParam.getMainFieldVal().length() != Integer.parseInt(validateParam.getArgumentsVal())) {
                return XResult.failure(validateParam.getMainFieldName() + "必须为" + validateParam.getArgumentsVal() + "位");
            }
        } else {
            boolean inLengthRange = validateParam.getMainFieldVal().length() >= Integer.parseInt(args[0]) &&
                    validateParam.getMainFieldVal().length() <= Integer.parseInt(args[1]);
            if (!inLengthRange) {
                return XResult.failure(validateParam.getMainFieldName() + "必须大于等于" + args[0] + "位和小于等于" + args[1] + "位");
            }
        }
        return XResult.success();
    }

    @Override
    public String getMethodAttr() {
        return "l";
    }
}
