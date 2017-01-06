package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.AbstractValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 参数数值范围校验
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public class RangeCheck_in extends AbstractValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        String argValues = validateParam.getArgumentsVal();
        String[] split = argValues.split(",");
        for (String e : split) {
            if (validateParam.getMainFieldVal().equals(e)) {
                return XResult.success();
            }
        }
        int lastIndex = argValues.lastIndexOf(",");

        String argComment = lastIndex == -1
                ? argValues.substring(0, lastIndex) + "或" + argValues.substring(lastIndex+1, argValues.length())
                : argValues;
        return XResult.failure(getFieldAlias(validateParam) + "必须为" + argComment.replaceAll(",", "、"));
    }

    @Override
    public String getMethodAttr() {
        return "in";
    }
}
