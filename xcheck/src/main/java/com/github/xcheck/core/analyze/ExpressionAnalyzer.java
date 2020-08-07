package com.github.xcheck.core.analyze;

import com.github.xcheck.core.item.CheckItem;

/**
 * 公式解析接口
 * @Author chenjw
 * @Date 2017年02月03日
 */
public interface ExpressionAnalyzer {

    /**
     * 解析表达式
     * @param expression
     * @return
     */
    CheckItem analyze(String expression);
}
