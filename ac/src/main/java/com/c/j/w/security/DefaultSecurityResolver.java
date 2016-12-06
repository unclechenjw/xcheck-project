package com.c.j.w.security;

import com.c.j.w.security.redis.dao.JedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 杭州动享互联网技术有限公司
 *
 * @Author chenjw
 * @Date 2016年12月06日
 */
@Primary
@Component
public class DefaultSecurityResolver implements SecurityResolveAdapter {

    @Autowired
    private JedisManager jm;

    @Override
    public void responseVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            jm.set("1234", 60, "x");
            response.setContentType("application/json;charset=utf-8");
            writer = response.getWriter();
            writer.write("{\"status\":450,\"message\":\"请输入验证码1234\"}");
        } catch (IOException var8) {
            var8.printStackTrace();
        } finally {
            if(writer != null) {
                writer.close();
            }
        }
    }

    @Override
    public void ipAccessDenied(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setContentType("application/json;charset=utf-8");
            writer = response.getWriter();
            writer.write("{\"status\":300,\"message\":\"访问太频繁啦,休息一下\"}");
        } catch (IOException var8) {
            var8.printStackTrace();
        } finally {
            if(writer != null) {
                writer.close();
            }
        }
    }

    @Override
    public void telAccessDenied(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setContentType("application/json;charset=utf-8");
            writer = response.getWriter();
            writer.write("{\"status\":300,\"message\":\"访问太频繁啦,休息一会\"}");
        } catch (IOException var8) {
            var8.printStackTrace();
        } finally {
            if(writer != null) {
                writer.close();
            }
        }
    }
}
