package com.cmy.xcheck.util.jy.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cmy.xcheck.config.ErrorMessageBuilder;
import com.cmy.xcheck.exception.ExpressionDefineException;
import com.cmy.xcheck.util.CheckResult;
import com.cmy.xcheck.util.Validator;
import com.cmy.xcheck.util.jy.ValidationHandler;

public enum ValidationSimpleHandler implements ValidationHandler {
    
    INSTANCE;
    
    public void validate(Map<String, String> requestParam, String express, CheckResult cr) {
        
        String field;
        ArrayList<FieldMap> fmArray; // 字段名与val值
        String formulas;
        String prompt = null; // 错误提示
        
        int fieldBehindIndex = 0;
        int atIndex = express.indexOf("@");
        int numberSignIndex = express.indexOf("#");
        // 错误信息提示分隔符
        int tildeIndex = express.indexOf(":");
        
        if (atIndex == -1 && numberSignIndex == -1) {
            // 无校验方法，只要字段不为空则校验通过
            if (tildeIndex == -1) {
                field = express;
            } else {
                field = express.substring(0, tildeIndex);
            }
            // 同时处理单字段与多字段[]校验
            fmArray = getVal(field, requestParam);
            for (FieldMap fm : fmArray) {
                if (Validator.isEmpty(fm.getVal())) {
                    if (tildeIndex == -1) {
                        cr.setErrorMsg(fm.getField() + " can not be null");
                    } else {
                        cr.setErrorMsg(
                                express.substring(tildeIndex+1, express.length()));
                    }
                    // 如果校验不通过退出方法
                    return;
                }
            }
            // 字段不为空校验结束
            return;
        } else if (atIndex != -1) {
            fieldBehindIndex = atIndex;
        } else if (numberSignIndex != -1) {
            fieldBehindIndex = numberSignIndex;
        } else {
            fieldBehindIndex = atIndex > numberSignIndex ? numberSignIndex : atIndex;
        }
        
        field = express.substring(0, fieldBehindIndex);
        
        fmArray = getVal(field, requestParam);
        // check配置的校验提示
        if (tildeIndex == -1) {
            formulas = express.substring(fieldBehindIndex, express.length());
        } else {
            formulas = express.substring(fieldBehindIndex, tildeIndex);
            prompt = express.substring(tildeIndex+1, express.length());
        }
        
        // 开始公式校验
        validate0(formulas, fmArray, prompt, cr);
    }
    
    /**
     * 获取单字段或多字段[]字段名与值
     * @param field
     * @param source
     * @return
     */
    private ArrayList<FieldMap> getVal(String field, Map<String, String> requestParam) {
        ArrayList<FieldMap> array = new ArrayList<FieldMap>();
        
        if (field.startsWith("[")) {
            String[] split = field.substring(1, field.length()-1).split(",");
            for (String f : split) {
                array.add(new FieldMap(f, requestParam.get(f)));
            }
        } else {
            array.add(new FieldMap(field, requestParam.get(field)));
        }
        return array;
    }
    
    private void validate0(String formulas, ArrayList<FieldMap> fmArray,
            String prompt, CheckResult result) {
        Matcher formulaMatcher = FORMULA_PARSING_PATT.matcher(formulas);
        while (formulaMatcher.find()) {
            if (result.isNotPass()) {
                return;
            }
            validateField(formulaMatcher, fmArray, prompt, result);
        }
    }
    
    private void validateField(Matcher formulaMatcher, ArrayList<FieldMap> fmArray,
            String prompt, CheckResult result) {

        String formula = formulaMatcher.group();
        String invokeType = formulaMatcher.group(1);
        String methodAbbr = formulaMatcher.group(2);
        String arguments  = formulaMatcher.group(3);
        for (FieldMap fm : fmArray) {
            if ("#".equals(invokeType) &&
                    Validator.isEmpty(fm.getVal())) {
                // # 允许字段为null
                continue;
            } else {
                try {
                    // @ 校验首先判断字段是否为空
                    String val = fm.getVal();
                    if (Validator.isEmpty(val)) {
                        result.setErrorMsg(fm.getField() + " can not be null");
                        return;
                    }
                    Boolean calculate = Validator.calculate(methodAbbr, fm.getVal(), arguments);
                    if (!calculate) {
                        String buildMsg = ErrorMessageBuilder.buildMsg(
                                methodAbbr, fm.getField(), arguments, prompt);
                        result.setErrorMsg(buildMsg);
                        return;
                    }
                } catch (Exception e) {
                    throw new ExpressionDefineException(formula, e);
                }
            }
        }
    }
    // 表达式：字段@或#（方法名（参数）？）多组（&公式&） 可有可无
//    private static final Pattern EXPRESS_PARSING_PATT = Pattern.compile(
//            "([a-zA-Z0-9]+)([@#a-zA-Z0-9$]+\\(.*\\))+(&(.*?)&)?");
    private static final Pattern FORMULA_PARSING_PATT = Pattern.compile(
            "(@|#)([a-zA-Z0-9$]*)(?:\\((.*?)\\))?");
    
    private class FieldMap {
        private String field;
        private String val;
        public FieldMap(String field, String val) {
            super();
            this.field = field;
            this.val = val;
        }
        public String getField() {
            return field;
        }
        public String getVal() {
            return val;
        }
    }
}

