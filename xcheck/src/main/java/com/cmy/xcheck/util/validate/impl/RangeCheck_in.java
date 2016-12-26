package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 参数数值范围校验
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public class RangeCheck_in implements ValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        String[] split = validateParam.getArgumentsVal().split(",");
        for (String e : split) {
            if (validateParam.getMainFieldVal().equals(e)) {
                return XResult.success();
            }
        }
        return XResult.failure(validateParam.getMainFieldName() + "必须为" + validateParam.getArgumentsVal().replaceAll(",", "、"));
    }

    @Override
    public String getMethodAttr() {
        return "in";
    }
}
