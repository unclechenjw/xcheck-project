package com.cmy.xcheck.util.analyze;

import com.cmy.xcheck.exception.UnsupportedExpressionException;
import com.cmy.xcheck.util.Assert;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.impl.XCheckItemSimple;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidatePack;
import com.cmy.xcheck.util.validate.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析普通表达式
 * Created by Kevin72c on 2016/5/2.
 */
@Component
@Scope("prototype")
public class SimpleExpressionAnalyzer {

    @Autowired
    private ValidatorFactory validatorFactory;
    private static final int Method_Abbreviation_Index = 2;
    private static final int Argument_Index = 3;

    /** 普通表达式捕获pattern */
    Pattern SIMPLE_EXPRESSION_PATTERN = Pattern.compile(
            "(@|#)([a-zA-Z0-9$]*)(?:\\((.*?)\\))?");

    public XCheckItem analyze(String expression) {
        Assert.simpleExpressionIllegal(expression);

        boolean nullable;
        int fieldBehindIndex;
        int atIndex = expression.indexOf("@");
        int numberSignIndex = expression.indexOf("#");
        int colonIndex = expression.indexOf(":");

        // @校验参数不可空
        if (atIndex != -1) {
            nullable = false;
            fieldBehindIndex = atIndex;
            // #校验参数允许空
        } else if (numberSignIndex != -1) {
            nullable = true;
            fieldBehindIndex = numberSignIndex;
            // 无公式校验,参数不可空
        } else if (colonIndex != -1) {
            nullable = false;
            fieldBehindIndex = colonIndex;
        } else {
            nullable = false;
            fieldBehindIndex = expression.length();
        }

        // 取得校验字段数组
        List<String> fields = getFieldArray(expression.substring(0, fieldBehindIndex));

        String message; // 自定义错误提示
        String checkRule; // 校验规则
        if (colonIndex == -1) {
            checkRule = expression.substring(fieldBehindIndex, expression.length());
            message = null;
        } else {
            checkRule = expression.substring(fieldBehindIndex, colonIndex);
            message = expression.substring(colonIndex+1, expression.length());
        }

        // 公式取得
        Matcher matcher = SIMPLE_EXPRESSION_PATTERN.matcher(checkRule);
        List<ValidatePack> validatePacks = new ArrayList<>();
        String methodAbbr; // 校验方法缩写
        String arguments; // 方法参数
        while (matcher.find()) {
            methodAbbr = matcher.group(Method_Abbreviation_Index);
            arguments = matcher.group(Argument_Index);
            ValidateMethod validateMethod = validatorFactory.getValidatorByAbbr(methodAbbr);
            if (validateMethod == null) {
                throw new UnsupportedExpressionException("校验公式设置不正确：" + expression +
                        "，不支持的校验方法:" + methodAbbr);
            }
            validatePacks.add(
                    new ValidatePack(validateMethod, arguments));
        }
        if (validatePacks.isEmpty()) {
            validatePacks.add(new ValidatePack(null, null));
        }
        return new XCheckItemSimple(validatePacks, fields, message, nullable);
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
