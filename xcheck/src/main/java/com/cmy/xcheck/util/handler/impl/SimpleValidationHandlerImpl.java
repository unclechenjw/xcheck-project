package com.cmy.xcheck.util.handler.impl;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.StringUtil;
import com.cmy.xcheck.util.Validator;
import com.cmy.xcheck.util.XMessageBuilder;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.impl.XCheckItemSimple;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidatePack;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SimpleValidationHandlerImpl implements ValidationHandler {

    @Override
    public XResult validate(XBean xBean, XCheckItem checkItem, Map<String, String[]> requestParams) {
        XCheckItemSimple checkItemSimple = (XCheckItemSimple) checkItem;
        List<ValidatePack> validatePacks = checkItemSimple.getValidatePacks();

        for (ValidatePack vp : validatePacks) {
            ValidateMethod validateMethod = vp.getValidateMethod();
            List<String> fields = checkItemSimple.getFields();
            for (String field : fields) {
                String[] params = requestParams.get(field);

                if (params == null) {
                    if (checkItemSimple.isNullable()) {
                        continue;
                    } else {
                        return XResult.failure(field + "不能为空");
                    }
                }

                for (String param : params) {
                    if (StringUtil.isEmpty(param)) {
                        if (checkItemSimple.isNullable()) {
                            continue;
                        } else {
                            return XResult.failure(field + "不能为空");
                        }
                    }

                    ValidateParam validateParam = new ValidateParam();
                    validateParam.setMainFieldName(field);
                    validateParam.setMainFieldVal(param);
                    validateParam.setArgumentsVal(vp.getArguments());
                    XResult xResult = validateMethod.validate(validateParam);
                    if (xResult.isNotPass()) {
                        xResult.setMessage(field + xResult.getMessage());
                        return xResult;
                    }
                }
            }
        }
        return XResult.success();
    }
}

