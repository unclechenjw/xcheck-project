package com.c.j.w.xcheck.core.validate.impl;

import com.c.j.w.xcheck.core.validate.ValidateParam;
import com.c.j.w.xcheck.core.XResult;
import com.c.j.w.xcheck.core.util.StringUtil;
import com.c.j.w.xcheck.core.validate.AbstractValidateMethod;
import org.springframework.stereotype.Component;

/**
 * 非字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component("W")
public class NonLetter_W extends AbstractValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        if (StringUtil.isAllNotLetter(validateParam.getMainFieldVal())) {
            return XResult.success();
        } else {
            return XResult.failure(getFieldAlias(validateParam) + "不能包含任何字母");
        }
    }

}
