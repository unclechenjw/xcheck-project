package com.cmy.xcheck.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.cmy.xcheck.support.annotation.Check;
import com.cmy.xcheck.util.XResult;
import com.cmy.xcheck.util.Validator;
import com.cmy.xcheck.util.jy.OperationFactory;


public class XCheckSupport {

    /**
     * 校验入口
     * @return
     */
    public static XResult check(Method method, HttpServletRequest request) {
        // 判断是否验证对象
        XResult cr = new XResult();
        
        try {
            boolean isAnnotationPresent = method.isAnnotationPresent(Check.class);
            // 带有Check注解的方法进行参数规则校验
            if (isAnnotationPresent) {
                Map<String, String> requestParam = prepareRequestParam(request);
                // TODO delete it
                formule2Check(method, requestParam, cr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cr.failure("数据校验异常");
        }
        return cr;
    }

    /**
     * 公式校验
     * @param method
     * @param jsonParam
     * @param cr
     * void返回类型
     */
    private static void formule2Check(Method method, Map<String, String> requestParam,
            XResult cr) {
        Check check = method.getAnnotation(Check.class);
        if (check != null) {
            
            // 验证用户是否登录
            if (!verifySessionUser(check, requestParam, cr)) {
                return;
            }
            
            String[] value = check.value();
            // 遍历公式
            for (String formula : value) {
                if (Validator.isEmpty(formula)) {
                    continue;
                }
                // 公式校验
                OperationFactory.validateFormula(formula, requestParam, cr);
                // 如果校验不通过退出循环
                if (cr.isNotPass()) {
                    return;
                }
            }
        }
    }


    /**
     * ParameterMap转Map
     * 摈弃数组参数下标1以后的值
     * @param request
     * @return 返回JSON对象
     */
    private static Map<String, String> prepareRequestParam(HttpServletRequest request) {
        Map<String, String> requestParam = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        Iterator<Entry<String, String[]>> iterator = entrySet.iterator();
        Entry<String, String[]> entry;
        String value[];
        while (iterator.hasNext()) {
            entry = iterator.next();
            value = entry.getValue();
            if (value != null && value.length > 0) {
                requestParam.put(entry.getKey(), value[0]);
            }
        }
        return requestParam;
    }
    

//  @Resource
//  private static final JedisManager jm = SysContext.getBean("jedisManager", JedisManager.class);

    private static boolean verifySessionUser(Check check, Map<String, String> requestParam,
            XResult cr) {
        // 判断用户是否登录
        if (check.required()) {
            // 如果会话令牌为空
            String sessionToken = requestParam.get("sessionToken");
            if (Validator.isEmpty(sessionToken)) {
                cr.failure("用户未登录");
                cr.setStatus(1100); //TODO 未登录状态需要修改
                return false;
            }

            // redis获取用户信息
//            SessionUser sessionUser = (SessionUser) jm.getObject(
//                    CommonKeyEnum.token_app_user, requestParam.get("userID"));
            // 或者会话令牌不匹配，提示用户未登录
//            if (sessionUser == null
//                    || !sessionUser.getSessionToken().equals(sessionToken)) { // token不匹配
//                cr.setErrorMsg("用户未登录");
//                cr.setStatus(100);
//                return false;
//            }
        }
        return true;
    }
}
