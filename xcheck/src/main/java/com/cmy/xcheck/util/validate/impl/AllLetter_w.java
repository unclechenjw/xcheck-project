package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.StringUtil;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 全字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public class AllLetter_w implements ValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        for (char aChar : validateParam.getMainFieldVal().toCharArray()) {
            if (!Character.isLetter(aChar)) {
                return XResult.failure("必须全字母");
            }
        }
        return XResult.success();
    }

    @Override
    public String getMethodAttr() {
        return "w";
    }
}
