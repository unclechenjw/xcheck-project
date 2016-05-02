package com.cmy.xcheck.support;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cmy.xcheck.support.annotation.Check;
import com.cmy.xcheck.support.annotation.XAnnotationConfigApplicationContext;
import com.cmy.xcheck.util.XCheckDispatcher;

public class XCheckSupport {

    /**
     * 校验入口
     * @return
     */
    public static XResult check(Method method, HttpServletRequest request) {
        // 判断是否验证对象
        XResult xResult = new XResult();
        boolean isAnnotationPresent = method.isAnnotationPresent(Check.class);
        // 带有Check注解的方法进行参数规则校验
        if (isAnnotationPresent) {
            Check check = method.getAnnotation(Check.class);
            XBean xBean = XAnnotationConfigApplicationContext.getXBean(check);
            Map<String, String[]> requestParam = prepareRequestParam(request);
            XCheckDispatcher.execute(xBean, requestParam, xResult);
        }
        return xResult;
    }

    /**
     * ParameterMap转Map
     * 摈弃数组参数下标1以后的值
     * @param request
     * @return 返回JSON对象
     */
    private static Map<String, String[]> prepareRequestParam(HttpServletRequest request) {
        return request.getParameterMap();
    }
}
