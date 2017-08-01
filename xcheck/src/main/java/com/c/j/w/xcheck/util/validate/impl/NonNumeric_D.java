package com.c.j.w.xcheck.util.validate.impl;

import com.c.j.w.xcheck.util.validate.ValidateParam;
import com.c.j.w.xcheck.support.XResult;
import com.c.j.w.xcheck.util.StringUtil;
import com.c.j.w.xcheck.util.validate.AbstractValidateMethod;
import com.c.j.w.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 非字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component("D")
public class NonNumeric_D extends AbstractValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        if (StringUtil.isAllNotDigit(validateParam.getMainFieldVal())) {
            return XResult.success();
        } else {
            return XResult.failure(getFieldAlias(validateParam) + "不能包含任何数字");
        }
    }

}
