package com.c.j.w.security;

import com.c.j.w.security.annotation.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Author chenjw
 * @Date 2016年12月06日
 */
public interface SecurityResolveAdapter {

    /**
     * 生成验证码响应
     * @param request
     * @param response
     */
    void responseVerifyCode(HttpServletRequest request, HttpServletResponse response, Security security, String message);

    /**
     * Ip拒绝访问响应
     * @param request
     * @param response
     */
    void ipAccessDenied(HttpServletRequest request, HttpServletResponse response);

    /**
     * 手机号码拒绝访问响应
     * @param request
     * @param response
     */
    void telAccessDenied(HttpServletRequest request, HttpServletResponse response);
}
