package com.c.j.w.xcheck.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.c.j.w.xcheck.support.XCheckHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.c.j.w.xcheck.util.CheckDispatcher;
import com.c.j.w.xcheck.support.XResult;

@Component
public class XCheckSpringMVCInterceptor implements HandlerInterceptor {

    @Autowired
    private CheckDispatcher xCheckSupport;
    @Autowired
    private XCheckHandlerAdapter xCheckHandlerAdapter;

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object paramObject,
            Exception paramException) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object paramObject,
            ModelAndView paramModelAndView) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object paramObject) throws Exception {
        if (paramObject instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)paramObject;
            // xcheck校验入口
            XResult checkResult = xCheckSupport.check(handlerMethod.getMethod(), request);
            if (checkResult.isNotPass()) {
                // 返回校验不通过原因,错误信息， status：响应状态, message：校验不通过原因
                xCheckHandlerAdapter.checkFailHandle(request, response, paramObject, checkResult.getMessage());
                return false;
            }
        }
        return true;
    }

}
