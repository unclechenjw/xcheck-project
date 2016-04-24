package com.cmy.xcheck.util.jy.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cmy.xcheck.exception.ExpressionDefineException;
import com.cmy.xcheck.util.CheckResult;
import com.cmy.xcheck.util.jy.OperationFactory;
import com.cmy.xcheck.util.jy.ValidationHandler;

public enum ValidationIfExpressionHandler implements ValidationHandler {

    INSTANCE;

    // 公式if('condition','condition',('condition')?)(:prompt)?
    private static final Pattern IF_FORMULA_PARSING_PATT = Pattern
            //TODO
            .compile("^if\\('(.*?)','(.*?)'(?:,'(.*?)')?\\)(?:\\:(.*?))?$");
    
    public void validate(Map<String, String> requestParam, String express,
            CheckResult cr) {
        Matcher m = IF_FORMULA_PARSING_PATT.matcher(express.replaceAll("\\s", ""));
        String firstCondition;
        String secondCondition;
        String thirdCondition;
        String prompt; // 错误提示
        if (m.find()) {
            firstCondition = m.group(1);
            secondCondition = m.group(2);
            thirdCondition = m.group(3);
            prompt = m.group(4);
            OperationFactory.validateFormula(firstCondition, requestParam, cr);
            if (cr.isPass()) {
                OperationFactory.validateFormula(secondCondition, requestParam, cr);
            } else {
                if (thirdCondition != null) {
                    OperationFactory.validateFormula(thirdCondition, requestParam, cr);
                }
            }
            if (prompt != null) {
                cr.setErrorMsg(prompt);
            }
        } else {
            throw new ExpressionDefineException(express);
        }
    }
    
    public static void main(String[] args) {
//        Pattern p = Pattern.compile("^if\\('(.*?)','(.*?)'(?:,'(.*?)')?\\)(?:~(.*?))?$");
        String f1 = "if('a>b', 'a@b', 'sd# b' )~xxx";
//        String f2 = "if('a>b','a@b')~asd";
        System.out.println(f1.replaceAll("\\s", ""));
        Matcher m = IF_FORMULA_PARSING_PATT.matcher(f1.replaceAll("\\s", ""));
        while (m.find()) {
            System.out.println(m.group());
            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));
            System.out.println(m.group(4));
        }
    }
    
}

