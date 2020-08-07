package com.github.xcheck.core.analyze.impl;

import com.github.xcheck.exception.ExpressionDefineException;
import com.github.xcheck.core.analyze.ExpressionAnalyzer;
import com.github.xcheck.core.item.XCheckItem;
import com.github.xcheck.core.item.impl.XCheckItemLogic;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析逻辑表达式
 * @author chenjw
 * @date 2016/5/2
 **/
@Component
@Scope("prototype")
public class LogicExpressionAnalyzer implements ExpressionAnalyzer {

    private static final Pattern COMPARISON_OPERATOR_PATTERN =
            Pattern.compile("(.*?)(<=|<|>=|>|==|!=)(.*)");

    @Override
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
