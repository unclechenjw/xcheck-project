package com.cmy.xcheck.util.handler.impl;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.StringUtil;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.impl.XCheckItemSimple;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidatePack;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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

                if (StringUtil.isEmpty(params)) {
                    if (checkItemSimple.isNullable()) {
                        continue;
                    } else {
                        return XResult.failure(field + "不能为空");
                    }
                }

                if (validateMethod == null) {
                    continue;
                }
                for (String param : params) {
                    ValidateParam validateParam = new ValidateParam(
                            field,
                            param,
                            preparingArguments(vp.getArguments(), requestParams),
                            xBean.getFieldAlias());
                    XResult xResult = validateMethod.validate(validateParam);
                    if (xResult.isNotPass()) {
                        return xResult;
                    }
                }
            }
        }
        return XResult.success();
    }

    private String preparingArguments(String arg, Map<String, String[]> requestParams) {
        if (StringUtil.isEmpty(arg) || StringUtil.isAllDigit(arg)) {
            return arg;
        }
        if (arg.contains(",")) {
            String[] split = arg.split(",");
            StringBuilder sb = new StringBuilder();
            for (String e : split) {
                sb.append(preparingArguments_(e, requestParams)).append(",");
            }
            return sb.deleteCharAt(sb.length()-1).toString();
        } else {
            return preparingArguments_(arg, requestParams);
        }
    }
    private String preparingArguments_(String arg, Map<String, String[]> requestParams) {
        String[] values = requestParams.get(arg);
        if (values == null || StringUtil.isEmpty(values[0])) {
            return arg;
        } else {
            return values[0];
        }
    }
}

