package com.cmy.xcheck.util.jy;

import java.util.Map;

import com.cmy.xcheck.ExpressionTypeEnum;
import com.cmy.xcheck.util.CheckResult;
import com.cmy.xcheck.util.jy.impl.ValidationIfExpressionHandler;
import com.cmy.xcheck.util.jy.impl.ValidationLogicalHandler;
import com.cmy.xcheck.util.jy.impl.ValidationSimpleHandler;

public class OperationFactory {

    public static void validateFormula(String formula, Map<String, String> requestParam,
            CheckResult cr) {
        ExpressionTypeEnum expressType = getExpressType(formula);
        ValidationHandler oh = null;
        switch (expressType) {
        case EXPRESS_TYPE_IF_FORMULA:
            oh = ValidationIfExpressionHandler.INSTANCE;
            break;
        case EXPRESS_TYPE_MULTIATTRIBUTE:
//            oh = ValidationMultiFieldHandler.INSTANCE;
            oh = ValidationSimpleHandler.INSTANCE;
            break;
        case EXPRESS_TYPE_LOGICAL_OPERATION:
            oh = ValidationLogicalHandler.INSTANCE;
            break;
        case EXPRESS_TYPE_SIMPLE:
            oh = ValidationSimpleHandler.INSTANCE;
            break;
        }
        oh.validate(requestParam, formula, cr);
    }

    private static ExpressionTypeEnum getExpressType(String express) {
        ExpressionTypeEnum expressType;
        if (express.startsWith("if")) {
            expressType = ExpressionTypeEnum.EXPRESS_TYPE_IF_FORMULA;
        } else if (express.startsWith("[")) {
            expressType = ExpressionTypeEnum.EXPRESS_TYPE_MULTIATTRIBUTE;
        } else if (express.matches("(.*?)(<=|<|>=|>|==|!=)(.*)")) {
            expressType = ExpressionTypeEnum.EXPRESS_TYPE_LOGICAL_OPERATION;
        } else {
            expressType = ExpressionTypeEnum.EXPRESS_TYPE_SIMPLE;
        }
        return expressType;
    }
}
