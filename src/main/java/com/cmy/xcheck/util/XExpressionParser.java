package com.cmy.xcheck.util;

import com.cmy.xcheck.ExpressionTypeEnum;
import com.cmy.xcheck.config.XMessageBuilder;
import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.annotation.Check;
import com.cmy.xcheck.support.annotation.XAnnotationConfigApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表达式解析
 * Created by Kevin72c on 2016/4/29.
 */
public class XExpressionParser {

    /**
     * 扫描解析校验对象
     * @param classes
     */
    public static void parseXbean(Set<Class<?>> classes) {

        for (Class<?> clz : classes) {
            Method[] methods = clz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Check.class)) {
                    parseXbean_(clz, method);
                }
            }
        }
    }

    /**
     * 解析校验对象
     * @param clz
     * @param method
     */
    public static void parseXbean_(Class<?> clz, Method method) {
        Check check = method.getAnnotation(Check.class);
        String[] values = check.value();
        Map<String, String> fieldAlias = parseFieldAliasToMap(check.fieldAlias());
        boolean hint = check.hint();
        boolean required = check.required();
        XBean.CheckItem[] checkItems = new XBean.CheckItem[values.length];
        for (int i = 0; i < values.length; i++) {
            checkItems[i] = parseExpression(values[i]);
        }
        String identify = clz.getName() + "$" + method.getName();
        XBean xBean = new XBean.Builder().fieldAlias(fieldAlias).checkItems(checkItems).require(required).hint(hint).build();
        XAnnotationConfigApplicationContext.register(identify, xBean);
    }

    /**
     * 获取单字段或多字段[]字段名与值
     * @param fields
     * @return
     */
    private static String[] getField(String fields) {
        if (fields.startsWith("[")) {
            String[] split = fields.substring(1, fields.length()-1).split(",");
            return split;
        } else {
            return new String[]{fields};
        }
    }

    /**
     * 字段别名转map
     * @param fieldAlias
     * @return
     */
    private static Map<String, String> parseFieldAliasToMap(String[] fieldAlias) {
        Map<String, String> m = new HashMap<String, String>();
        for (String alias : fieldAlias) {
            String[] split = alias.replaceAll("\\s", "").split(",");
            for (String sp :split) {
                String[] fieldAndName = sp.split("=");
                int fieldNameLen = fieldAndName.length;
                if ( fieldNameLen == 2) {
                    m.put(fieldAndName[0], fieldAndName[1]);
                } else {
                    throw new IllegalArgumentException(XMessageBuilder.getProperty("fieldAliasError"));
                }
            }
        }
        return m;
    }


    /**
     * 解析表达式类型
     * @param expression
     * @return
     */
    private static XBean.CheckItem parseExpression(String expression) {
        XBean.CheckItem checkItem;
        ExpressionTypeEnum expressType = calcExpressType(expression);
        if (expressType == ExpressionTypeEnum.EXPRESSION_TYPE_IF_CONDITION) {
            // TODO: 2016/4/30
            checkItem = expressTypeSimple(expression, expressType);
        } else if (expressType == ExpressionTypeEnum.EXPRESSION_TYPE_LOGICAL_OPERATION) {
            // TODO: 2016/4/30
            checkItem = expressTypeSimple(expression, expressType);
        } else {
            // 普通表达式转换
            checkItem = expressTypeSimple(expression, expressType);
        }
        return checkItem;
    }

    /**
     * 获取表达式类型
     * @param expression
     * @return
     */
    private static ExpressionTypeEnum calcExpressType(String expression) {
        ExpressionTypeEnum expressType;
        if (expression.startsWith("if")) {
            expressType = ExpressionTypeEnum.EXPRESSION_TYPE_IF_CONDITION;
        } else if (expression.matches("(.*?)(<=|<|>=|>|==|!=)(.*)")) {
            expressType = ExpressionTypeEnum.EXPRESSION_TYPE_LOGICAL_OPERATION;
        } else {
            expressType = ExpressionTypeEnum.EXPRESSION_TYPE_SIMPLE;
        }
        return expressType;
    }

    private static final Pattern FORMULA_PARSING_PATTERN = Pattern.compile(
            "(@|#)([a-zA-Z0-9$]*)(?:\\((.*?)\\))?");
    /**
     * 解析普通表达式
     * @param expression
     * @param expressionType
     * @return
     */
    public static XBean.CheckItem expressTypeSimple(String expression, ExpressionTypeEnum expressionType) {
        Assert.expressionIllegal(expression);

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
        String[] fields = getField(expression.substring(0, fieldBehindIndex));

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
        Matcher matcher = FORMULA_PARSING_PATTERN.matcher(formula);
        String method;
        String argument;
        while (matcher.find()) {
            method = matcher.group(2);
            argument = matcher.group(3);

        }
        return new XBean.CheckItem(formula, fields, message, expressionType, nullable);
    }

}
