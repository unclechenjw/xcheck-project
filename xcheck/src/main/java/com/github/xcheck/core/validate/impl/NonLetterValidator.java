package com.github.xcheck.core.validate.impl;

import com.github.xcheck.core.validate.ValidateParam;
import com.github.xcheck.core.XResult;
import com.github.xcheck.core.util.StringUtil;
import com.github.xcheck.core.validate.AbstractValidateMethod;
import org.springframework.stereotype.Component;

/**
 * 非字母
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component("W")
public class NonLetterValidator extends AbstractValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        if (StringUtil.isAllNotLetter(validateParam.getMainFieldVal())) {
            return XResult.success();
        } else {
            return XResult.failure(getFieldAlias(validateParam) + "不能包含任何字母");
        }
    }

}
