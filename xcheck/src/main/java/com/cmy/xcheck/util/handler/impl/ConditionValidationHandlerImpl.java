package com.cmy.xcheck.util.handler.impl;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.item.XCheckItem;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConditionValidationHandlerImpl implements ValidationHandler {

    // 公式if('condition','condition',('condition')?)(:prompt)?
    private static final Pattern IF_Formula_Parsing_Pattern = Pattern
            //TODO
            .compile("^if\\('(.*?)','(.*?)'(?:,'(.*?)')?\\)(?:\\:(.*?))?$");

    @Override
    public XResult validate(XBean xBean, XCheckItem checkItem, Map<String, String[]> requestParams) {
        return null;
    }

    public static void main(String[] args) {
//        Pattern p = Pattern.compile("^if\\('(.*?)','(.*?)'(?:,'(.*?)')?\\)(?:~(.*?))?$");
        String f1 = "if('a>b', 'a@b', 'sd# b' )~xxx";
//        String f2 = "if('a>b','a@b')~asd";
        System.out.println(f1.replaceAll("\\s", ""));
        Matcher m = IF_Formula_Parsing_Pattern.matcher(f1.replaceAll("\\s", ""));
        while (m.find()) {
            System.out.println(m.group());
            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));
            System.out.println(m.group(4));
        }
    }
    
}

