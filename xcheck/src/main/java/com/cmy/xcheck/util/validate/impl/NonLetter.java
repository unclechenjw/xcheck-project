package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 非字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public class NonLetter implements ValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
//        if (args == null || args.length == 0 || args[0] == null || args.length == 0) {
//            return XResult.failure("can not be empty");
//        }
//        for (char aChar : args[0].toCharArray()) {
//            if (!Character.isLetter(aChar)) {
//                return XResult.failure("不能包含字母");
//            }
//        }
        return XResult.success();
    }

    @Override
    public String getMethodAttr() {
        return "W";
    }
}
