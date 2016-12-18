package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.ValidateMethod;
import org.springframework.stereotype.Component;

/**
 * 非字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public class AllNumeric implements ValidateMethod {

    @Override
    public XResult validate(String... args) {
        if (args == null || args.length == 0 || args[0] == null || args.length == 0) {
            return XResult.failure("can not be empty");
        }
        for (char aChar : args[0].toCharArray()) {
            if (!Character.isDigit(aChar)) {
                return XResult.failure("必须全数字");
            }
        }
        return XResult.success();
    }

    @Override
    public String getMethodAttr() {
        return "d";
    }
}
