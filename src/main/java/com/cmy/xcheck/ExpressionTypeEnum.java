package com.cmy.xcheck;

public enum ExpressionTypeEnum {

    EXPRESS_TYPE_IF_FORMULA(0),
    EXPRESS_TYPE_MULTIATTRIBUTE(1),
    EXPRESS_TYPE_LOGICAL_OPERATION(2),
    EXPRESS_TYPE_SIMPLE(3);
    
    int type;
    ExpressionTypeEnum(int type) {
        this.type = type;
    }
    
}