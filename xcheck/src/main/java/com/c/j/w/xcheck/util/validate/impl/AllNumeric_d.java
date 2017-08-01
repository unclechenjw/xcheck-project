package com.c.j.w.xcheck.util.validate.impl;

import com.c.j.w.xcheck.util.validate.AbstractValidateMethod;
import com.c.j.w.xcheck.util.validate.ValidateParam;
import com.c.j.w.xcheck.support.XResult;
import com.c.j.w.xcheck.util.StringUtil;
import com.c.j.w.xcheck.util.validate.AbstractValidateMethod;
import com.c.j.w.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

/**
 * 全数字
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component("d")
public class AllNumeric_d extends AbstractValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        if (StringUtil.isAllDigit(validateParam.getMainFieldVal())) {
            return XResult.success();
        } else {
            return XResult.failure(getFieldAlias(validateParam) + "必须为全数字");
        }
    }

}
