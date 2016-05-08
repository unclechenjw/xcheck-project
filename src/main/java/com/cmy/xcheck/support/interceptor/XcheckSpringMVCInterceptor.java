package com.cmy.xcheck.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cmy.xcheck.support.XCheckSupport;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.ResponseWriter;

public class XcheckSpringMVCInterceptor implements HandlerInterceptor {

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
            XResult checkResult = XCheckSupport.check(handlerMethod.getMethod(),
                    request);
            if (checkResult.isNotPass()) {
                // 返回校验不通过原因,错误信息， status：300, message：校验结果
                ResponseWriter.writeMessage(response, checkResult.getStatus(),
                        checkResult.getMessage());
                return false;
            }
        }
        return true;
    }

}
