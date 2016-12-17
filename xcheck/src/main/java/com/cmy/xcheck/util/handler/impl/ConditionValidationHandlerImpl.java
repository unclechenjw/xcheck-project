package com.cmy.xcheck.util.handler.impl;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.item.XCheckItem;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConditionValidationHandlerImpl implements ValidationHandler {

    // 公式if('condition','condition',('condition')?)(:prompt)?
    private static final Pattern IF_FORMULA_PARSING_PATT = Pattern
            //TODO
            .compile("^if\\('(.*?)','(.*?)'(?:,'(.*?)')?\\)(?:\\:(.*?))?$");

    @Override
    public XResult validate(XBean xBean, XCheckItem checkItem, HttpServletRequest request) {
        return null;
    }

    public void validate(Map<String, String> requestParam, String express,
                         XResult cr) {

//        Matcher m = IF_FORMULA_PARSING_PATT.matcher(express.replaceAll("\\s", ""));
//        String firstCondition;
//        String secondCondition;
//        String thirdCondition;
//        String prompt; // 错误提示
//        if (m.find()) {
//            firstCondition = m.group(1);
//            secondCondition = m.group(2);
//            thirdCondition = m.group(3);
//            prompt = m.group(4);
//            operationFactory.validateFormula(firstCondition, requestParam, cr);
//            if (cr.isPass()) {
//                operationFactory.validateFormula(secondCondition, requestParam, cr);
//            } else {
//                if (thirdCondition != null) {
//                    operationFactory.validateFormula(thirdCondition, requestParam, cr);
//                }
//            }
//            if (prompt != null) {
//                cr.failure(prompt);
//            }
//            cr.failure("多条件校验维护中,请暂时不要使用");
//        } else {
//            throw new ExpressionDefineException(express);
//        }
        cr.failure("多条件校验维护中,请暂时不要使用");
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

