package com.cmy.xcheck.test;

import com.cmy.xcheck.config.XMessageBuilder;
import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.annotation.ClassPathXBeanDefinitionScanner;
import com.cmy.xcheck.support.annotation.XAnnotationConfigApplicationContext;
import com.cmy.xcheck.util.XExpressionParser;

import java.util.*;

/**
 * Created by Kevin72c on 2016/4/30.
 */
public class XcheckTest {
    static Map<String, String[]> requestParam = new HashMap<String, String[]>();
    static {
        Set<Class<?>> classes = ClassPathXBeanDefinitionScanner.scanXBean("com.cmy.xcheck");
        XExpressionParser.parseXbean(classes);

        requestParam.put("a", new String[]{"x"});
        requestParam.put("b", new String[]{"a2"});
    }
    public static void main(String[] args) {
        Map<String, XBean> x = XAnnotationConfigApplicationContext.x;
        System.out.println(x);
        XBean xBean = x.get("com.cmy.xcheck.test.Action$test0");
        simple(xBean);
    }

    public static void simple(XBean xBean) {

        XBean.CheckItem[] checkItems = xBean.getCheckItems();
        for (XBean.CheckItem checkItem : checkItems) {
            List<XBean.FormulaItem> formulaItems = checkItem.getFormulaItems();
            for (XBean.FormulaItem formulaItem : formulaItems)
                for (String field : checkItem.getFields()) {
                    String[] values = requestParam.get(field);
                    for (String value : values) {
                        Boolean calculate = formulaItem.calculate(value);
                        if (!calculate) {
                            String s = XMessageBuilder.buildMsg(formulaItem.getMethodAbbr(), field, formulaItem.getArgument(), xBean, checkItem);
                            System.out.println(s);
                        }
                    }
                }
        }
    }
}
