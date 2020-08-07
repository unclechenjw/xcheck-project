package com.github.xcheck.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户会话有效校验接口
 *
 * @Author chenjw
 * @Date 2016年09月10日
 */
public interface CheckHandlerAdapter {

    /**
     * 参数校验不通过处理方法
     *
     * @param request
     * @param response
     * @param failMessage 校验不通过原因
     */
    void checkFailHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         String failMessage);

}
