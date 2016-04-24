package com.cmy.xcheck;

public enum ExpressionTypeEnum {

    EXPRESSION_TYPE_IF_CONDITION(0),
    EXPRESSION_TYPE_LOGICAL_OPERATION(1),
    EXPRESSION_TYPE_SIMPLE(2);
    
    int type;
    ExpressionTypeEnum(int type) {
        this.type = type;
    }
    
}