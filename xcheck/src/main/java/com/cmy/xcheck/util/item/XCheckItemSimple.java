package com.cmy.xcheck.util.item;

import com.cmy.xcheck.util.Validator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 普通表达式实体
 * Created by Kevin72c on 2016/5/2.
 */

public class XCheckItemSimple implements XCheckItem {
    private List<FormulaItem> formulaItems;
    private List<String> fields;
    private String message;
    private boolean nullable;

    public XCheckItemSimple(List<FormulaItem> formulaItems, final List<String> fields, String message, boolean nullable) {
        super();
        this.formulaItems = formulaItems;
        this.fields = fields;
        this.message = message;
        this.nullable = nullable;
    }

    public List<String> getFields() {
        return fields;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isNullable() {
        return nullable;
    }

    public List<FormulaItem> getFormulaItems() {
        return formulaItems;
    }

    public static class FormulaItem {
        private String methodAbbr;
        private Method method;
        private String argument;
        private int argNum;

        public FormulaItem(String methodAbbr, Method method, int argNum, String argument) {
            this.methodAbbr = methodAbbr;
            this.method = method;
            this.argNum = argNum;
            this.argument = argument;
        }

        public Boolean calculate(String val) {
            try {
                if (argNum == 0) {
                    return (Boolean) method.invoke(Validator.INSTANCE, val);
                } else {
                    return (Boolean) method.invoke(Validator.INSTANCE, val, argument);
                }
            } catch (Exception e) {
                // TODO: 2016/5/1
                e.printStackTrace();
                return false;
            }
        }

        public String getMethodAbbr() {
            return methodAbbr;
        }

        public String getArgument() {
            return argument;
        }
    }
}