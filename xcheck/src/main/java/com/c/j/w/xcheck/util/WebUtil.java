package com.c.j.w.xcheck.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: chenjw
 * @Date: 2017/11/26 下午9:00
 */
public class WebUtil {

    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
    }

    public static HttpServletResponse getCurrentResponse() {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
    }

}
