package com.cmy.xcheck.util.handler;

import java.util.Map;

import com.cmy.xcheck.ExpressionTypeEnum;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.handler.impl.SimpleValidationHandlerImpl;

public class OperationFactory {

    public static void validateFormula(String formula, Map<String, String> requestParam,
            XResult cr) {
        ExpressionTypeEnum expressType = getExpressType(formula);
        ValidationHandler oh = null;
        switch (expressType) {
        case EXPRESSION_TYPE_IF_CONDITION:
//            oh = ConditionValidationHandlerImpl.INSTANCE;
            break;
//        case EXPRESS_TYPE_MULTIATTRIBUTE:
//            oh = ValidationMultiFieldHandler.INSTANCE;
//            oh = SimpleValidationHandlerImpl.INSTANCE;
//            break;
        case EXPRESSION_TYPE_LOGICAL_OPERATION:
//            oh = LogicValidationHandlerImpl.INSTANCE;
            break;
        case EXPRESSION_TYPE_SIMPLE:
            oh = SimpleValidationHandlerImpl.INSTANCE;
            break;
        }
        // TODO
//        oh.validate(requestParam, formula, cr);
    }

    private static ExpressionTypeEnum getExpressType(String express) {
        ExpressionTypeEnum expressType;
        if (express.startsWith("if")) {
            expressType = ExpressionTypeEnum.EXPRESSION_TYPE_IF_CONDITION;
//        } else if (express.startsWith("[")) {
//            expressType = ExpressionTypeEnum.EXPRESS_TYPE_MULTIATTRIBUTE;
        } else if (express.matches("(.*?)(<=|<|>=|>|==|!=)(.*)")) {
            expressType = ExpressionTypeEnum.EXPRESSION_TYPE_LOGICAL_OPERATION;
        } else {
            expressType = ExpressionTypeEnum.EXPRESSION_TYPE_SIMPLE;
        }
        return expressType;
    }
}
