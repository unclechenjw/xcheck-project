package com.c.j.w.xcheck.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户会话有效校验接口
 * @Author chenjw
 * @Date 2016年09月10日
 */
public interface XCheckHandlerAdapter {

    /**
     * 参数校验不通过处理方法
     * @param request
     * @param response
     * @param paramObject HandlerInterceptor拦截器的方法preHandle第三个参数
     * @param failMessage 校验不通过原因
     */
    void checkFailHandle(HttpServletRequest request,
                          HttpServletResponse response,
                          Object paramObject,
                          String failMessage);

}
