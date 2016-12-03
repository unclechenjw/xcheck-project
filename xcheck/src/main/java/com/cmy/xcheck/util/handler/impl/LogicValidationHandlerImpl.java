package com.cmy.xcheck.util.handler.impl;

import com.cmy.xcheck.exception.ExpressionDefineException;
import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.Validator;
import com.cmy.xcheck.util.XMessageBuilder;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.XCheckItemLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

@Component
public class LogicValidationHandlerImpl implements ValidationHandler {

    @Autowired
    private XMessageBuilder xMessageBuilder;


    private static final ScriptEngine JS_ENGINE =
            new ScriptEngineManager().getEngineByName("javascript");

    @Override
    public void validate(XBean xBean, XCheckItem checkItem, XResult xResult, Map<String, String[]> requestParam) {
        XCheckItemLogic checkItemLogic = (XCheckItemLogic) checkItem;
        String[] leftVal = requestParam.get(checkItemLogic.getLeftField());
        String[] rightVal = requestParam.get(checkItemLogic.getRightField());
        // prepare values
        if (leftVal == null || Validator.isEmpty(leftVal[0])) {
            String message = xMessageBuilder.buildMsg(checkItemLogic.getLeftField(),
                    "CanNotBeNull", xBean, checkItem);
            xResult.failure(message);
            return;
        }
        String finalLeftVal = leftVal[0];
        String finalRightVal;
        if (Validator.isDecimal(checkItemLogic.getRightField())) {
            finalRightVal = checkItemLogic.getRightField();
        } else {
            if (rightVal == null || Validator.isEmpty(rightVal[0])) {
                String message = xMessageBuilder.buildMsg(checkItemLogic.getRightField(),
                        "CanNotBeNull", xBean, checkItem);
                xResult.failure(message);
                return;
            } else {
                finalRightVal = rightVal[0];
            }
        }

        // == 或 != 作为字符串比较
//        if ("==".equals(checkItemLogic.getComparisonOperator()) ||
//            "!=".equals(checkItemLogic.getComparisonOperator())) {
//            finalLeftVal = "'" + finalLeftVal + "'";
//            finalRightVal = "'" + finalRightVal + "'";
//        }
        boolean isArgError = Validator.isDecimal(finalLeftVal) && Validator.isDecimal(finalRightVal);
        if (!isArgError) {
            xResult.failure(xMessageBuilder.getProperty("ParameterError"));
            return;
        }
        // 比较公式
        String formula =  finalLeftVal + checkItemLogic.getComparisonOperator() + finalRightVal;
        try {
            boolean bl =  (Boolean) JS_ENGINE.eval(formula);
            if (!bl) {
                xResult.failure(xMessageBuilder.buildLogicErrorMessage(xBean, checkItemLogic));
            }
        } catch (ScriptException e) {
            throw new ExpressionDefineException(
                    "比较表达式有误 公式：" + formula, e);
        }
    }
}
