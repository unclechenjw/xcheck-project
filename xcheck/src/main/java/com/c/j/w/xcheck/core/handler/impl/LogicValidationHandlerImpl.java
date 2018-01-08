package com.c.j.w.xcheck.core.handler.impl;

import com.c.j.w.xcheck.exception.ExpressionDefineException;
import com.c.j.w.xcheck.core.XBean;
import com.c.j.w.xcheck.core.XResult;
import com.c.j.w.xcheck.core.util.StringUtil;
import com.c.j.w.xcheck.core.util.XHelper;
import com.c.j.w.xcheck.core.handler.ValidationHandler;
import com.c.j.w.xcheck.core.item.XCheckItem;
import com.c.j.w.xcheck.core.item.impl.XCheckItemLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class LogicValidationHandlerImpl implements ValidationHandler {

    @Autowired
    private XHelper xHelper;
    private static final ScriptEngine JS_ENGINE = new ScriptEngineManager().getEngineByName("javascript");
    private static final Pattern Date_Format_Pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s?(\\d{1,2}:\\d{1,2}(:\\d{1,3})?)?");

    @Override
    public XResult validate(XBean xBean, XCheckItem checkItem, Map<String, String[]> requestParams) {
        XCheckItemLogic checkItemLogic = (XCheckItemLogic) checkItem;
        String[] leftValues = requestParams.get(checkItemLogic.getLeftField());
        String[] rightValues;
        if (StringUtil.isAllDigit(checkItemLogic.getRightField())) {
            rightValues = new String[]{checkItemLogic.getRightField()};
        } else {
            rightValues = requestParams.get(checkItemLogic.getRightField());
        }
        if (leftValues == null) {
            return XResult.failure(checkItemLogic.getLeftField() + "不能为空");
        }
        if (rightValues == null) {
            return XResult.failure(checkItemLogic.getRightField() + "不能为空");
        }
        if (leftValues.length != rightValues.length) {
            return XResult.failure(checkItemLogic.getLeftField() + "或" + checkItemLogic.getRightField() + "填写不正确");
        }
        return compareEachFiled(leftValues, rightValues, xBean, checkItemLogic);
    }

    private String getValue(String field, String fieldValue) {
        if (StringUtil.isDecimal(field)) {
            return field;
        } else {
            if (isDateFormat(fieldValue)) {
                return toLongDate(fieldValue);
            } else {
                return fieldValue;
            }
        }
    }
    private boolean isDateFormat(String str) {
        return str != null && Date_Format_Pattern.matcher(str).matches();
    }

    /**
     * 转长整形字符日期
     */
    private String toLongDate(String date){
        try {
            if (date.length() == 10) {
                return String.valueOf(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime());
            } else {
                return String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").parse(date).getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private XResult compareEachFiled(String[] leftValues, String[] rightValues, XBean xBean, XCheckItemLogic checkItemLogic) {
        for (int i = 0; i < leftValues.length; i++) {
            String finalLeftVal = getValue(checkItemLogic.getLeftField(), leftValues[i]);
            if (StringUtil.isEmpty(finalLeftVal)) {
                return XResult.failure(checkItemLogic.getLeftField() + "不能为空");
            }
            String finalRightVal = getValue(checkItemLogic.getRightField(), rightValues[i]);
            if (StringUtil.isEmpty(finalRightVal)) {
                return XResult.failure(checkItemLogic.getRightField() + "不能为空");
            }

            // 比较公式
            String formula =  finalLeftVal + checkItemLogic.getComparisonOperator() + finalRightVal;
            try {
                boolean valid =  (Boolean) JS_ENGINE.eval(formula);
                if (valid) {
                    continue;
                } else {
                    String message = xHelper.getAlias(checkItemLogic.getLeftField(), xBean.getFieldAlias())
                            + "必须" + xHelper.getProperty(checkItemLogic.getComparisonOperator())
                            + xHelper.getAlias(checkItemLogic.getRightField(), xBean.getFieldAlias());
                    return XResult.failure(message);
                }
            } catch (ScriptException e) {
                throw new ExpressionDefineException("比较表达式有误 公式：" + formula, e);
            }
        }
        return XResult.success();
    }

}
