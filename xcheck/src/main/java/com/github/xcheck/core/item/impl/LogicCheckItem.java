package com.github.xcheck.core.item.impl;

import com.github.xcheck.core.item.CheckItem;

/**
 * 逻辑表达式
 * @author chenjw
 * @date 2016/5/2
 **/
public class LogicCheckItem implements CheckItem {

    private String leftField;
    private String rightField;
    private String message;
    private String comparisonOperator;

    public LogicCheckItem(String leftField, String rightField, String comparisonOperator, String message) {
        this.leftField = leftField;
        this.rightField = rightField;
        this.comparisonOperator = comparisonOperator;
        this.message = message;
    }

    public String getLeftField() {
        return leftField;
    }

    public String getRightField() {
        return rightField;
    }

    public String getMessage() {
        return message;
    }

    public String getComparisonOperator() {
        return comparisonOperator;
    }
}