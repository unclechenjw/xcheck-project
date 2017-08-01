package com.c.j.w.xcheck.util.analyze.impl;

import com.c.j.w.xcheck.exception.ExpressionDefineException;
import com.c.j.w.xcheck.util.analyze.ExpressionAnalyzer;
import com.c.j.w.xcheck.util.item.XCheckItem;
import com.c.j.w.xcheck.util.item.impl.XCheckItemLogic;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析逻辑表达式
 * Created by Kevin72c on 2016/5/2.
 */
@Component
@Scope("prototype")
public class LogicExpressionAnalyzer implements ExpressionAnalyzer {

    private static final Pattern COMPARISON_OPERATOR_PATTERN =
            Pattern.compile("(.*?)(<=|<|>=|>|==|!=)(.*)");

    public XCheckItem analyze(String expression) {
        // 提示信息分隔符
        String[] split = expression.split(":");
        String message = split.length > 1 ? split[1] : null;

        Matcher matcher = COMPARISON_OPERATOR_PATTERN.matcher(split[0]);
        if (!matcher.find()) {
            throw new ExpressionDefineException("公式定义不正确：" + expression);
        }
        // 解析公式
        String leftField = matcher.group(1);
        String comparisonOperator = matcher.group(2);
        String rightField = matcher.group(3);

        return new XCheckItemLogic(leftField, rightField, comparisonOperator, message);
    }

}
