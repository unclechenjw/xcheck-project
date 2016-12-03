package com.cmy.xcheck.support;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cmy.xcheck.support.annotation.Check;
import com.cmy.xcheck.support.annotation.XAnnotationConfigApplicationContext;
import com.cmy.xcheck.util.XCheckDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XCheckSupport {

    @Autowired
    private XCheckDispatcher xCheckDispatcher;

    /**
     * 校验入口
     * @return
     */
    public XResult check(Method method, HttpServletRequest request) {
        // 判断是否验证对象
        XResult xResult = new XResult();
        boolean isAnnotationPresent = method.isAnnotationPresent(Check.class);
        // 带有Check注解的方法进行参数规则校验
        if (isAnnotationPresent) {
            Check check = method.getAnnotation(Check.class);
            XBean xBean = XAnnotationConfigApplicationContext.getXBean(check);
            if (xBean == null) {
                throw new RuntimeException("未配置扫描校验对象,请在XCheckContext中配置ControllerPackage路径");
            }
            Map<String, String[]> requestParam = prepareRequestParam(request);
            xCheckDispatcher.execute(xBean, requestParam, xResult);
        }
        return xResult;
    }

    /**
     * ParameterMap转Map
     * 摈弃数组参数下标1以后的值
     * @param request
     */
    private static Map<String, String[]> prepareRequestParam(HttpServletRequest request) {
        return request.getParameterMap();
    }
}
