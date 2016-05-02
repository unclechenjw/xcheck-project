package com.cmy.xcheck.util.analyze;

import com.cmy.xcheck.util.Assert;
import com.cmy.xcheck.util.Validator;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.XCheckItemSimple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kevin72c on 2016/5/2.
 */
public class LogicExpressionAnalyzer {

    /**
     * 解析普通表达式
     * @param expression
     * @return
     */
    public static XCheckItem analyze(String expression) {
        Assert.expressionIllegal(expression);

//        return new XCheckItemSimple(formulaItems, fields, message, nullable);
        return null;
    }


    /**
     * 获取单字段或多字段[]字段名与值
     * @param fields
     * @return
     */
    private static String[] getFieldArray(String fields) {
        if (fields.startsWith("[")) {
            String[] split = fields.substring(1, fields.length()-1).split(",");
            return split;
        } else {
            return new String[]{fields};
        }
    }
}
