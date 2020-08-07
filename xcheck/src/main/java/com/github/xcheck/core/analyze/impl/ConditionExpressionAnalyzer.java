package com.github.xcheck.core.analyze.impl;

import com.github.xcheck.core.item.XCheckItem;
import com.github.xcheck.core.util.StringUtil;
import com.github.xcheck.core.analyze.ExpressionAnalyzer;
import com.github.xcheck.core.item.impl.XCheckItemCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 条件表达式解析
 * Created by Kevin72c on 2016/5/2.
 */
@Component
@Scope("prototype")
public class ConditionExpressionAnalyzer implements ExpressionAnalyzer {

    @Autowired
    private SimpleExpressionAnalyzer simpleExpressionAnalyzer;
    @Autowired
    private LogicExpressionAnalyzer logicExpressionAnalyzer;
    private static final Pattern Analyze_Pattern = Pattern.compile("^if\\('(.*?)','(.*?)'(?:,'(.*?)')?\\)(?:\\:(.*?))?$");
    private static final String ERROR_MSG = "if条件表达式设置有误，正确应该为if('必填条件','必填条件','选填条件'):选填提示信息";

    public XCheckItem analyze(String expression) {
        Matcher m = Analyze_Pattern.matcher(expression.replaceAll("\\s", ""));
        if (!m.find()) {
            throw new IllegalArgumentException(ERROR_MSG + "请检查\""+ expression + "\",是否正确");
        }
        String firstExp = m.group(1);
        String secondExp = m.group(2);
        String thirdExp = m.group(3);
        String message = m.group(4);
        XCheckItem firstItem = getCheckItem(firstExp);
        XCheckItem secondItem = getCheckItem(secondExp);
        XCheckItem thirdItem = null;
        if (StringUtil.isNotEmpty(thirdExp)) {
            thirdItem = getCheckItem(thirdExp);
        }
        return new XCheckItemCondition(firstItem, secondItem, thirdItem, message);
    }

    private XCheckItem getCheckItem(String expression) {
        if (StringUtil.isEmpty(expression)) {
            throw new IllegalArgumentException(ERROR_MSG + "请检查\""+ expression + "\",是否正确");
        }
        if (expression.matches("(.*?)(<=|<|>=|>|==|!=)(.*)")) {
            // 逻辑比较表达式
            return logicExpressionAnalyzer.analyze(expression);
        } else {
            // 普通表达式
            return simpleExpressionAnalyzer.analyze(expression);
        }
    }

}
