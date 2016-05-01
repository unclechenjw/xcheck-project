package com.cmy.xcheck.util.jy.impl;

import java.util.Map;
import java.util.regex.Pattern;

import com.cmy.xcheck.config.XMessageBuilder;
import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.Validator;
import com.cmy.xcheck.util.jy.ValidationHandler;

public enum ValidationSimpleHandler implements ValidationHandler {
//public enum ValidationSimpleHandler {

    INSTANCE;

    public void validate(Map<String, String[]> requestParam, XBean xBean, XResult xResult) {
        XBean.CheckItem[] checkItems = xBean.getCheckItems();
        String[] fields;
        for (XBean.CheckItem item : checkItems) {
            fields = item.getFields();
            if (item.canNotBeNull()) {
                validateFields(fields, requestParam, xBean, item, xResult);
            }
            if (xResult.isNotPass()) {
                return;
            }

        }
    }

//    private void validate0(String formulas, ArrayList<FieldMap> fmArray,
//                           String prompt, XResult result) {
//        Matcher formulaMatcher = FORMULA_PARSING_PATT.matcher(formulas);
//        while (formulaMatcher.find()) {
//            if (result.isNotPass()) {
//                return;
//            }
//            validateField(formulaMatcher, fmArray, prompt, result);
//        }
//    }

    /**
     * 校验null值
     * @param fields
     * @param requestParam
     * @param xBean
     * @param checkItem
     * @param xResult
     */
    private void validateFields(String[] fields, Map<String, String[]> requestParam, XBean xBean, XBean.CheckItem checkItem, XResult xResult) {
        for (String field : fields) {
            String[] values = requestParam.get(field);
            for (String val : values) {
                if (Validator.isEmpty(val)) {
                    String message = XMessageBuilder.buildMsg(field, "CanNotBeNull", xBean, checkItem);
                    xResult.failure(message);
                    return;
                }
            }
        }
    }

//    public void validate(Map<String, String> requestParam, String expression, XResult cr) {
//
//        String field;
//        ArrayList<FieldMap> fmArray; // 字段名与val值
//        String formulas;
//        String prompt = null; // 错误提示
//
//        int fieldBehindIndex;
//        int atIndex = expression.indexOf("@");
//        int numberSignIndex = expression.indexOf("#");
//        // 错误信息提示分隔符
//        int tildeIndex = expression.indexOf(":");
//
//        if (atIndex == -1 && numberSignIndex == -1) {
//            // 无校验方法，只要字段不为空则校验通过
//            if (tildeIndex == -1) {
//                field = expression;
//            } else {
//                field = expression.substring(0, tildeIndex);
//            }
//            // 同时处理单字段与多字段[]校验
//            fmArray = getVal(field, requestParam);
//            for (FieldMap fm : fmArray) {
//                if (Validator.isEmpty(fm.getVal())) {
//                    if (tildeIndex == -1) {
//                        cr.failure(fm.getField() + " can not be null");
//                    } else {
//                        cr.failure(
//                                expression.substring(tildeIndex+1, expression.length()));
//                    }
//                    // 如果校验不通过退出方法
//                    return;
//                }
//            }
//            // 字段不为空校验结束
//            return;
//        } else if (atIndex != -1) {
//            fieldBehindIndex = atIndex;
//        } else if (numberSignIndex != -1) {
//            fieldBehindIndex = numberSignIndex;
//        } else {
//            fieldBehindIndex = atIndex > numberSignIndex ? numberSignIndex : atIndex;
//        }
//
//        field = expression.substring(0, fieldBehindIndex);
//
//        fmArray = getVal(field, requestParam);
//        // check配置的校验提示
//        if (tildeIndex == -1) {
//            formulas = expression.substring(fieldBehindIndex, expression.length());
//        } else {
//            formulas = expression.substring(fieldBehindIndex, tildeIndex);
//            prompt = expression.substring(tildeIndex+1, expression.length());
//        }
//
//        // 开始公式校验
//        validate0(formulas, fmArray, prompt, cr);
//    }
    
//    /**
//     * 获取单字段或多字段[]字段名与值
//     * @param field
//     * @param requestParam
//     * @return
//     */
//    private ArrayList<FieldMap> getVal(String field, Map<String, String> requestParam) {
//        ArrayList<FieldMap> array = new ArrayList<FieldMap>();
//
//        if (field.startsWith("[")) {
//            String[] split = field.substring(1, field.length()-1).split(",");
//            for (String f : split) {
//                array.add(new FieldMap(f, requestParam.get(f)));
//            }
//        } else {
//            array.add(new FieldMap(field, requestParam.get(field)));
//        }
//        return array;
//    }
    
//    private void validate0(String formulas, ArrayList<FieldMap> fmArray,
//            String prompt, XResult result) {
//        Matcher formulaMatcher = FORMULA_PARSING_PATT.matcher(formulas);
//        while (formulaMatcher.find()) {
//            if (result.isNotPass()) {
//                return;
//            }
//            validateField(formulaMatcher, fmArray, prompt, result);
//        }
//    }

    /**
     * 字段校验
     * @param formulaMatcher
     * @param fmArray
     * @param prompt
     * @param result
     */
//    private void validateField(Matcher formulaMatcher, ArrayList<FieldMap> fmArray,
//            String prompt, XResult result) {
//
//        String formula = formulaMatcher.group();
//        String invokeType = formulaMatcher.group(1);
//        String methodAbbr = formulaMatcher.group(2);
//        String arguments  = formulaMatcher.group(3);
//        for (FieldMap fm : fmArray) {
//            if ("#".equals(invokeType) &&
//                    Validator.isEmpty(fm.getVal())) {
//                // # 允许字段为null
//                continue;
//            } else {
//                try {
//                    // @ 校验首先判断字段是否为空
//                    String val = fm.getVal();
//                    if (Validator.isEmpty(val)) {
//                        result.failure(fm.getField() + " can not be null");
//                        return;
//                    }
//                    Boolean calculate = Validator.calculate(methodAbbr, fm.getVal(), arguments);
//                    if (!calculate) {
//                        String buildMsg = XMessageBuilder.buildMsg(
//                                methodAbbr, fm.getField(), arguments, prompt);
//                        result.failure(buildMsg);
//                        return;
//                    }
//                } catch (Exception e) {
//                    throw new ExpressionDefineException(formula, e);
//                }
//            }
//        }
//    }
    // 表达式：字段@或#（方法名（参数）？）多组（&公式&） 可有可无
//    private static final Pattern EXPRESS_PARSING_PATT = Pattern.compile(
//            "([a-zA-Z0-9]+)([@#a-zA-Z0-9$]+\\(.*\\))+(&(.*?)&)?");
    private static final Pattern FORMULA_PARSING_PATT = Pattern.compile(
            "(@|#)([a-zA-Z0-9$]*)(?:\\((.*?)\\))?");
    
//    private class FieldMap {
//        private String field;
//        private String val;
//        public FieldMap(String field, String val) {
//            super();
//            this.field = field;
//            this.val = val;
//        }
//        public String getField() {
//            return field;
//        }
//        public String getVal() {
//            return val;
//        }
//    }

}

