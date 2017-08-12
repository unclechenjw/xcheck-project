package com.c.j.w.sample.config;

import com.c.j.w.xcheck.support.XCheckProperties;
import com.c.j.w.xcheck.support.XCheckHandlerAdapter;
import com.c.j.w.xcheck.support.annotation.EnableXCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

/**
 * Created by kevin on 2016/12/5.
 */
@Configuration
@EnableXCheck
public class XConfig implements XCheckHandlerAdapter {

    @Bean
    public XCheckProperties xcheck() {
        // 配置全局字段注释
        String alias = "userID=用户ID, userName=用户名, userType=用户类型,keyword=关键字";
        XCheckProperties xCheckContext = new XCheckProperties();
        xCheckContext.setErrorMessageDisplay(false);
        xCheckContext.setLocale(Locale.CHINESE);
        xCheckContext.setControllerPackage(new String[]{"com.c.j.w.sample.controller"});
        xCheckContext.setFiledAlias(alias);
        return xCheckContext;
    }

    /**
     * 设置校验不通过时响应处理
     * @param request
     * @param response
     * @param paramObject HandlerInterceptor拦截器的方法preHandle第三个参数
     * @param failMessage 校验不通过原因
     */
    @Override
    public void checkFailHandle(HttpServletRequest request, HttpServletResponse response, Object paramObject, String failMessage) {
        PrintWriter writer = null;
        try {
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.write("{\"status\":" + 300 + ",\"message\":\"" + failMessage + "\"}");
        } catch (IOException var8) {
            var8.printStackTrace();
        } finally {
            if(writer != null) {
                writer.close();
            }
        }
    }

}
