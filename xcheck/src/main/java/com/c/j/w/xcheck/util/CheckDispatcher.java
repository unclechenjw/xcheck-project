package com.c.j.w.xcheck.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.c.j.w.xcheck.config.context.XAnnotationConfigApplicationContext;
import com.c.j.w.xcheck.support.XBean;
import com.c.j.w.xcheck.support.XCheckHandlerAdapter;
import com.c.j.w.xcheck.support.XResult;
import com.c.j.w.xcheck.support.annotation.Check;
import com.c.j.w.xcheck.util.handler.ValidationHandler;
import com.c.j.w.xcheck.util.handler.XFactory;
import com.c.j.w.xcheck.util.item.XCheckItem;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckDispatcher {

    @Autowired(required = false)
    private XCheckHandlerAdapter xCheckHandlerAdapter;
    @Autowired
    private XFactory xFactory;
    private static String Square_Brackets = "[";
    private static String Content_Type = "content-type";
    private static String Application_JSON = "application/json";

    /**
     * 校验入口
     *
     * @return
     */
    public XResult check(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        // 判断是否验证对象
        boolean isAnnotationPresent = method.isAnnotationPresent(Check.class);
        // 带有Check注解的方法进行参数规则校验
        if (isAnnotationPresent) {
            Check check = method.getAnnotation(Check.class);
            XBean xBean = XAnnotationConfigApplicationContext.getXBean(check);
            if (xBean == null) {
                throw new RuntimeException("未配置扫描校验对象,请在XCheckContext中配置ControllerPackage路径");
            }
            return dispatch(xBean, method, joinPoint);
        }
        return XResult.success();
    }

    private XResult dispatch(XBean xBean, Method method, ProceedingJoinPoint joinPoint) {
        // 参数准备
        Map<String, String[]> requestParams = prepareRequestParams(xBean, method, joinPoint);
        List<XCheckItem> checkItems = xBean.getCheckItems();
        // 遍历表达式
        for (XCheckItem checkItem : checkItems) {
            ValidationHandler handler = xFactory.getCheckHandler(checkItem);
            XResult validate = handler.validate(xBean, checkItem, requestParams);
            if (validate.isNotPass()) {
                if (StringUtil.isNotEmpty(checkItem.getMessage())) {
                    validate.setMessage(checkItem.getMessage());
                }
                return validate;
            }
        }
        return XResult.success();
    }

    private Map<String, String[]> prepareRequestParams(XBean xBean, Method method, ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = WebUtil.getCurrentRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String[]> newParameterMap = new HashMap<>();
        parameterMap.entrySet().stream().forEach(entry -> {
            String k = entry.getKey();
            if (k.contains(Square_Brackets)) {
                newParameterMap.put(k.replaceAll("\\[\\d+]", ""), entry.getValue());
            } else {
                newParameterMap.put(k, entry.getValue());
            }
        });

        if (xBean.hasPathParam()) {
            Map<String, String> pathVariableMap = (Map<String, String>) request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
            pathVariableMap.entrySet().stream().forEach(
                    entry -> newParameterMap.put(entry.getKey(), new String[]{entry.getValue()})
            );
        }

        if (isParameterJSONFormat(request)) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    if (parameterAnnotations[i][j] instanceof RequestBody) {
                        Object jsonParam = joinPoint.getArgs()[i];
//                        JSON.parseObject(jsonParam)
                        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(jsonParam);
//                        jsonObject.
//                        Collection<Object> values = jsonObject.values();
//                        for (Object v : values) {
//                            System.out.println(v);
//                        }
                        jsonObject.entrySet().stream().forEach(entry -> {
                            put(newParameterMap, entry.getKey(), entry.getValue());
                        });

                    }
                }
            }

        }
        return newParameterMap;
    }

    private void put(Map<String, String[]> map, String key, Object value) {
        if (value instanceof JSONArray) {
            JSONArray array = (JSONArray) value;
            for (int i = 0; i < array.size(); i++) {
                put(map, key, array.get(i));
            }
        } else if (value instanceof JSONObject) {
            JSONObject object = (JSONObject) value;
            object.entrySet().stream().forEach(entry -> {
                // .点号连接对象属性
                put(map, key + "." + entry.getKey(), entry.getValue());
            });
        } else {
            String[] strings = map.get(key);
            String v = value == null ? null : value.toString();
            if (strings == null) {
                map.put(key, new String[]{v});
            } else {
                add(map, key, strings, v);
            }
        }
    }

    private void add(Map<String, String[]> map, String key, String[] strings, String append) {
//        new strings.length;
        String[] s = new String[strings.length+1];
        for (int i = 0; i < s.length-1; i++) {
            s[i] = strings[i];
        }
        s[s.length-1] = append;
        map.put(key, s);
    }

    private boolean isParameterJSONFormat(HttpServletRequest request) {
        String contentType = request.getHeader("content-type");
        return contentType != null && contentType.contains(Application_JSON) ? true : false;
    }

    /**
     * 检查校验响应是否正确配置
     */
    @PostConstruct
    public void checkEnv() {
        if (xCheckHandlerAdapter == null) {
            throw new RuntimeException("XCheckHandlerAdapter unimplemented");
        }
    }
}
