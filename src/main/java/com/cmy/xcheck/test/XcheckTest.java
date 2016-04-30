package com.cmy.xcheck.test;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.annotation.ClassPathXBeanDefinitionScanner;
import com.cmy.xcheck.support.annotation.XAnnotationConfigApplicationContext;
import com.cmy.xcheck.util.XExpressionParser;

import java.util.Map;
import java.util.Set;

/**
 * Created by Kevin72c on 2016/4/30.
 */
public class XcheckTest {
    static {
        Set<Class<?>> classes = ClassPathXBeanDefinitionScanner.scanXBean("com.cmy.xcheck");
        XExpressionParser.parseXbean(classes);
    }

    public static void main(String[] args) {
        Map<String, XBean> x = XAnnotationConfigApplicationContext.x;
        System.out.println(x);

    }

}
