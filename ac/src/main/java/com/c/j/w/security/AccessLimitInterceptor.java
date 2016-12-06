package com.c.j.w.security;

import com.c.j.w.security.annotation.Security;
import com.c.j.w.security.redis.dao.JedisKey;
import com.c.j.w.security.redis.dao.JedisManager;
import com.c.j.w.security.annotation.AccessCondition;
import com.c.j.w.security.util.IPAnalyser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 访问限制拦截器
 * @Description: 
 * 杭州动享互联网技术有限公司
 * @author chenjw
 * @date 2016年12月6日 下午11:20:08
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisManager jm;
    @Autowired
    private SecurityResolveAdapter securityResolveAdapter;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object paramObject) throws Exception {
        if (paramObject instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod)paramObject;
            Method method = hm.getMethod();
            // 验证码安全过滤
            boolean securityVerify = securityVerify(request, response, method);
            if (!securityVerify) {
                return false;
            }

            // 请求频率限制
            return accessLimit(request, response, method);
        }
        return true;
    }
    
    /**
     * 请求次数拦截处理
     * @param request
     * @param response
     * @param method
     * @return
     */
    private boolean accessLimit(HttpServletRequest request, 
            HttpServletResponse response, Method method) {
        boolean isAnnotationPresent = method.isAnnotationPresent(AccessCondition.class);
        // 如声明AccessLimitation注解
        if (!isAnnotationPresent) {
            return true;
        }
        AccessCondition accessCondition = method.getAnnotation(AccessCondition.class);
        LimitMethod limitMethod = accessCondition.limitMethod();

        boolean result;
        // 限制访问拦截
        if (limitMethod == LimitMethod.IP) {
            result = iPAccessLimit(request, response, accessCondition);
        } else if (limitMethod == LimitMethod.Tel) {
            result = telAccessLimit(request, response, accessCondition);
        } else {
            throw new IllegalAccessError("请设置访问限制处理方法:" + limitMethod.name);
        }
        return result;
    }

    private static final String VerifyCode = "verifyCode";

    /**
     * 校验通过返回true,否则false
     * @param request
     * @param response
     * @return
     */
    private boolean securityVerify(HttpServletRequest request, HttpServletResponse response, Method method) {
        if (!method.isAnnotationPresent(Security.class)) {
            return true;
        }
        Security security = method.getAnnotation(Security.class);
        String verifyCode = request.getParameter(VerifyCode);
        if (verifyCode != null && verifyCode.length() > 0) {
            if (jm.del(verifyCode) > 0) {
                // 验证码正确校验通过
                return true;
            } else {
                securityResolveAdapter.responseVerifyCode(request, response);
                return false;
            }
        }
        String key = JedisKey.Security_Access_Frequency + security.module();
        Long incr = jm.incr(key);
        // 延迟过期时间
        jm.expire(key, 300);
        if (incr > 3) {
            securityResolveAdapter.responseVerifyCode(request, response);
            return false;
        }
        return true;
    }

    /**
     * IP请求次数拦截处理
     * @param request
     * @param response
     * @param accessCondition
     * @return
     */
    private boolean iPAccessLimit(HttpServletRequest request, HttpServletResponse response,
                                  AccessCondition accessCondition) {
        // 获取请求ip
        String ipAddress = IPAnalyser.getIPAddress(request);
        String key = JedisKey.Security_IP + accessCondition.module() + ipAddress;
        Long increment = jm.incr(key);
        if (increment > accessCondition.limit()) {
            // 返回错误提示
            securityResolveAdapter.ipAccessDenied(request, response);
            return false;
        }
        // 延迟过期时间
        jm.expire(key, accessCondition.seconds());
        return true;
    }

    /**
     * 手机号请求次数拦截处理
     * @param request
     * @param response
     * @param accessCondition
     * @return
     */
    private boolean telAccessLimit(HttpServletRequest request, HttpServletResponse response,
                                   AccessCondition accessCondition) {
        String fieldNameOfTel = accessCondition.fieldNameOfTel();
        if (fieldNameOfTel == null || "".equals(fieldNameOfTel)) {
            throw new IllegalArgumentException("使用手机号码限制方式请在" + AccessCondition.class.getName() +
                    "注解中设置fieldNameOfTel属性");
        }
        String tel = request.getParameter(fieldNameOfTel);
        String key = JedisKey.Security_Tel + accessCondition.module() + tel;
        Long increment = jm.incr(key);
        if (increment > accessCondition.limit()) {
            // 返回错误提示
            securityResolveAdapter.telAccessDenied(request, response);
            return false;
        }
        // 延迟过期时间
        jm.expire(key, accessCondition.seconds());
        return true;
    }

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

}
