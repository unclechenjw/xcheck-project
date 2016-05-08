package com.cmy.xcheck.util.handler.impl;

import com.cmy.xcheck.exception.ExpressionDefineException;
import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.Validator;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.XCheckItemLogic;
import com.cmy.xcheck.util.item.XCheckItemSimple;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LogicValidationHandlerImpl implements ValidationHandler {
    
    INSTANCE;

    @Override
    public void validate(XBean xBean, XCheckItem checkItem, XResult xResult, Map<String, String[]> requestParam) {
        XCheckItemLogic checkItemLogic = (XCheckItemLogic) checkItem;
        String[] leftVal = requestParam.get(checkItemLogic.getLeftField());
        String[] rightVal = requestParam.get(checkItemLogic.getRightField());


    }
    private static final ScriptEngine JS_ENGINE =
            new ScriptEngineManager().getEngineByName("javascript");
    private static final Pattern COMPARISON_OPERATOR_PATTERN =
            Pattern.compile("(.*?)(<=|<|>=|>|==|!=)(.*)");

    public void validate(Map<String, String> requestParam, String express, XResult cr) {
        // 提示信息分隔符
        String[] split = express.split(":");
        String prompt = split.length > 1 ? split[1] : null;

        Matcher matcher = COMPARISON_OPERATOR_PATTERN.matcher(split[0]);
        if (!matcher.find()) {
            throw new ExpressionDefineException("公式定义不正确：" + express);
        }
        // 解析公式
        String left = matcher.group(1);
        String comparisonOperator = matcher.group(2);
        String right = matcher.group(3);

        // 取值
        String leftVal = requestParam.get(left);
        String rightVal;

        // 如比较字段为数值类型则直接比较,不在source中取值
        if (Validator.isNumeric(right)) {
            rightVal = right;
        } else {
            rightVal = requestParam.get(right);
        }

        String leftComment = left; //getFieldComment(left);
        String rightComment = right; //getFieldComment(right);

        // == 或 != 作为字符串比较
        if ("==".equals(comparisonOperator) || "!=".equals(comparisonOperator)) {
            leftVal = "'" + leftVal + "'";
            rightVal = "'" + rightVal + "'";
        } else {
            if (Validator.isEmpty(leftVal)) {
                cr.failure(leftComment + "不能为空");
                return;
            }
            if (Validator.isEmpty(rightVal)) {
                cr.failure(rightComment + "不能为空");
                return;
            }

            if (leftVal.matches(".*?[-/].*?")) {
                // 值包含-或者/ 表示日期类型比较
                leftVal = "new Date('" + leftVal + "')";
                rightVal = "new Date('" + rightVal + "')";
            } else {
                if (Validator.isNotNumeric(leftVal)){
                    cr.failure(leftComment + "输入不正确");
                    return;
                }
                if (Validator.isNotNumeric(rightVal)) {
                    cr.failure(rightComment + "输入不正确");
                    return;
                }
            }
        }

        // 比较公式
        String formula =  leftVal + comparisonOperator + rightVal;
        try {
            boolean bl =  (Boolean) JS_ENGINE.eval(formula);
            if (!bl) {
                if (prompt == null) {
                    // TODO: 2016/5/1
//                    cr.failure(leftComment + XMessageBuilder.getMsg(comparisonOperator) + rightComment);
                } else {
                    cr.failure(prompt);
                }
            }
        } catch (ScriptException e) {
            throw new ExpressionDefineException(
                    "比较表达式有误:" +  express + "," +
                    " 数值" + left + ":" + leftVal + "," + right + ":" + rightVal + "," +
                    " 公式：" + formula, e);
        }
    }

    
}
