package com.cmy.xcheck.test;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.util.XCheckDispatcher;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.support.annotation.Check;
import com.cmy.xcheck.support.annotation.ClassPathXBeanDefinitionScanner;
import com.cmy.xcheck.support.annotation.XAnnotationConfigApplicationContext;
import com.cmy.xcheck.util.XExpressionParser;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Kevin72c on 2016/4/30.
 */
public class XcheckTest {
    static Map<String, String[]> requestParam = new HashMap<String, String[]>();
    static {
        Set<Class<?>> classes = ClassPathXBeanDefinitionScanner.scanXBean("com.cmy.xcheck");
        XExpressionParser.parseXbean(classes);

        requestParam.put("a", new String[]{"1111111111"});
        requestParam.put("a.a", new String[]{"1121111111"});
        requestParam.put("b", new String[]{"22.2", "0.11"});
        requestParam.put("c", new String[]{"22", "0"});
    }
    public static void main(String[] args) throws Exception {
        Method test0 = Action.class.getMethod("test0");
        Check check = test0.getAnnotation(Check.class);
        XBean xBean = XAnnotationConfigApplicationContext.getXBean(check);
        XResult xResult = new XResult();
        XCheckDispatcher.execute(xBean, requestParam, xResult);
        System.out.println(xResult.isPass());
        System.out.println(xResult.getMessage());
    }

}
