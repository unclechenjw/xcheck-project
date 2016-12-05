package com.cmy.sample.config;

import com.cmy.xcheck.support.XCheckContext;
import com.cmy.xcheck.support.XCheckHandlerAdapter;
import com.cmy.xcheck.support.annotation.EnableXCheck;
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
    public XCheckContext xCheckContext() {
        String alias = "userID=用户ID, userName=用户名, userType=用户类型,keyword=关键字";
        XCheckContext xCheckContext = new XCheckContext();
        xCheckContext.setErrorMessageDisplay(false);
        xCheckContext.setLocale(Locale.CHINESE);
        xCheckContext.setControllerPackage(new String[]{"com.cmy.sample.controller"});
        xCheckContext.setFiledAlias(alias);
        return xCheckContext;
    }

    @Override
    public boolean verifySession(Map<String, String[]> requestParam) {
        return false;
    }

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

    @Override
    public void sessionExpireHandle(HttpServletRequest request, HttpServletResponse response, Object paramObject) {

    }
}
