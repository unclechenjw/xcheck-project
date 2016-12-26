package com.cmy.xcheck.util.handler.impl;

import com.cmy.xcheck.exception.ExpressionDefineException;
import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.StringUtil;
import com.cmy.xcheck.util.XMessageBuilder;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.impl.XCheckItemLogic;
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
    public XResult validate(XBean xBean, XCheckItem checkItem, Map<String, String[]> requestParams) {
        XCheckItemLogic checkItemLogic = (XCheckItemLogic) checkItem;
        String[] leftVal = requestParams.get(checkItemLogic.getLeftField());
        String[] rightVal = requestParams.get(checkItemLogic.getRightField());
        // prepare values
        if (leftVal == null || StringUtil.isEmpty(leftVal[0])) {
            String message = xMessageBuilder.buildMsg(checkItemLogic.getLeftField(),
                    "CanNotBeNull", xBean, checkItem);
            return XResult.failure(message);
        }
        String finalLeftVal = leftVal[0];
        String finalRightVal;
        if (StringUtil.isDecimal(checkItemLogic.getRightField())) {
            finalRightVal = checkItemLogic.getRightField();
        } else {
            if (rightVal == null || StringUtil.isEmpty(rightVal[0])) {
                String message = xMessageBuilder.buildMsg(checkItemLogic.getRightField(),
                        "CanNotBeNull", xBean, checkItem);
                return XResult.failure(message);
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
        boolean isArgError = StringUtil.isDecimal(finalLeftVal) && StringUtil.isDecimal(finalRightVal);
        if (!isArgError) {
            return XResult.failure("请求参数不正确");
        }
        // 比较公式
        String formula =  finalLeftVal + checkItemLogic.getComparisonOperator() + finalRightVal;
        try {
            boolean bl =  (Boolean) JS_ENGINE.eval(formula);
            if (!bl) {
                return XResult.failure(xMessageBuilder.buildLogicErrorMessage(xBean, checkItemLogic));
            } else {
                return XResult.success();
            }
        } catch (ScriptException e) {
            throw new ExpressionDefineException(
                    "比较表达式有误 公式：" + formula, e);
        }
    }
}
