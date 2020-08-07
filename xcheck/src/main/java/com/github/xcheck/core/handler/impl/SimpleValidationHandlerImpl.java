package com.github.xcheck.core.handler.impl;

import com.github.xcheck.core.XBean;
import com.github.xcheck.core.XResult;
import com.github.xcheck.core.util.StringUtil;
import com.github.xcheck.core.util.XHelper;
import com.github.xcheck.core.handler.ValidationHandler;
import com.github.xcheck.core.item.XCheckItem;
import com.github.xcheck.core.item.impl.XCheckItemSimple;
import com.github.xcheck.core.validate.ValidateMethod;
import com.github.xcheck.core.validate.ValidatePack;
import com.github.xcheck.core.validate.ValidateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SimpleValidationHandlerImpl implements ValidationHandler {

    @Autowired
    private XHelper xHelper;

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
                        String alias = xHelper.getAlias(field, xBean.getFieldAlias());
                        return XResult.failure(alias + "不能为空");
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

