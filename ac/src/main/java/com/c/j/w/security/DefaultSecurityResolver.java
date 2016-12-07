package com.c.j.w.security;

import com.c.j.w.security.annotation.Security;
import com.c.j.w.security.redis.dao.JedisKey;
import com.c.j.w.security.redis.dao.JedisManager;
import com.c.j.w.security.util.ImageGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

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
    public void responseVerifyCode(HttpServletRequest request, HttpServletResponse response, Security security, String message) {
        PrintWriter writer = null;
        try {
            int i = new Random().nextInt(1000);
            jm.set(JedisKey.Security_Code + "" + i, 60, ""); // 验证码有效时间60秒
            response.setContentType("application/json;charset=utf-8");
            writer = response.getWriter();
            writer.write("{\"status\":450,\"message\":\""+ message + i + "\",\"infobean\":\"" +
                            ImageGenerateUtil.geneBase64Img(i+"") + "\"}");
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
