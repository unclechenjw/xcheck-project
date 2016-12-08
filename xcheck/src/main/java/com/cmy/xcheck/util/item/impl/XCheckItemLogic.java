package com.cmy.xcheck.util.item.impl;

import com.cmy.xcheck.util.item.XCheckItem;

/**
 * 逻辑表达式
 * Created by Kevin72c on 2016/5/2.
 */
public class XCheckItemLogic implements XCheckItem {

    private String leftField;
    private String rightField;
    private String message;
    private String comparisonOperator;

    public XCheckItemLogic(String leftField, String rightField, String comparisonOperator, String message) {
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