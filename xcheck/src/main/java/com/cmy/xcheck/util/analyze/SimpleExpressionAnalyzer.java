package com.cmy.xcheck.util.analyze;

import com.cmy.xcheck.util.Assert;
import com.cmy.xcheck.util.Validator;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.impl.XCheckItemSimple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析普通表达式
 * Created by Kevin72c on 2016/5/2.
 */
public class SimpleExpressionAnalyzer {

    public static XCheckItem analyze(String expression) {
        Assert.simpleExpressionIllegal(expression);

        String formula;
        boolean nullable;
        int fieldBehindIndex;
        int atIndex = expression.indexOf("@");
        int numberSignIndex = expression.indexOf("#");

        // @校验参数不可空
        if (atIndex != -1) {
            nullable = false;
            fieldBehindIndex = atIndex;
            // #校验参数允许空
        } else if (numberSignIndex != -1) {
            nullable = true;
            fieldBehindIndex = numberSignIndex;
            // 无公式校验,参数不可空
        } else {
            nullable = false;
            fieldBehindIndex = expression.length();
        }

        // 取得字段数组
        List<String> fields = getFieldArray(expression.substring(0, fieldBehindIndex));

        // 自定义错误提示
        String message;
        int colonIndex = expression.indexOf(":");
        if (colonIndex == -1) {
            formula = expression.substring(fieldBehindIndex, expression.length());
            message = null;
        } else {
            formula = expression.substring(fieldBehindIndex, colonIndex);
            message = expression.substring(colonIndex+1, expression.length());
        }

        /** 普通表达式捕获pattern */
        Pattern SIMPLE_EXPRESSION_PATTERN = Pattern.compile(
                "(@|#)([a-zA-Z0-9$]*)(?:\\((.*?)\\))?");
        // 公式取得
        Matcher matcher = SIMPLE_EXPRESSION_PATTERN.matcher(formula);
        List<XCheckItemSimple.FormulaItem> formulaItems = new ArrayList<XCheckItemSimple.FormulaItem>();
        String methodAbbr;
        String argument;
        while (matcher.find()) {
            methodAbbr = matcher.group(2);
            argument = matcher.group(3);
            Validator.CheckMethod checkMethod = Validator.getCheckMethod(methodAbbr);
            formulaItems.add(new XCheckItemSimple.FormulaItem(methodAbbr, checkMethod.getMethod(), checkMethod.getArgNum(), argument));
        }
        return new XCheckItemSimple(formulaItems, fields, message, nullable);
    }


    /**
     * 获取单字段或多字段[]字段名与值
     * @param fields
     * @return
     */
    private static List<String> getFieldArray(String fields) {
        List<String> fieldList;
        if (fields.startsWith("[")) {
            String[] split = fields.substring(1, fields.length()-1).split(",");
            fieldList = Arrays.asList(split);
        } else {
            fieldList = Arrays.asList(fields);
        }
        return fieldList;
    }
}
