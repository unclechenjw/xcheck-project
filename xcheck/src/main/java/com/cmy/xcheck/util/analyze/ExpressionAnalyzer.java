package com.cmy.xcheck.util.analyze;

import com.cmy.xcheck.util.item.XCheckItem;

/**
 * 公式解析接口
 * @Author chenjw
 * @Date 2017年02月03日
 */
public interface ExpressionAnalyzer {

    XCheckItem analyze(String expression);
}
