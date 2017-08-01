package com.c.j.w.xcheck.util.analyze;

import com.c.j.w.xcheck.util.item.XCheckItem;
import com.c.j.w.xcheck.util.item.XCheckItem;

/**
 * 公式解析接口
 * @Author chenjw
 * @Date 2017年02月03日
 */
public interface ExpressionAnalyzer {

    XCheckItem analyze(String expression);
}
