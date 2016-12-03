package com.cmy.xcheck.util.item;

/**
 * 逻辑表达式
 * Created by Kevin72c on 2016/5/2.
 */
public class XCheckItemCondition implements XCheckItem {

    private String leftField;
    private String rightField;
    private String message;
    private String comparisonOperator;

    public XCheckItemCondition(String leftField, String rightField, String message, String comparisonOperator) {
        this.leftField = leftField;
        this.rightField = rightField;
        this.message = message;
        this.comparisonOperator = comparisonOperator;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLeftField() {
        return leftField;
    }

    public void setLeftField(String leftField) {
        this.leftField = leftField;
    }

    public String getRightField() {
        return rightField;
    }

    public void setRightField(String rightField) {
        this.rightField = rightField;
    }

    public String getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }
}