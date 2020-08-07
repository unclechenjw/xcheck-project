package com.github.xcheck.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xcheck.support.annotation.Check;
import com.github.xcheck.core.util.WebUtil;
import com.github.xcheck.core.handler.ValidationHandler;
import com.github.xcheck.core.handler.HandlerFactory;
import com.github.xcheck.core.item.CheckItem;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckDispatcher {

    private static final String Square_Brackets = "[";
    private static final String Application_JSON = "application/json";
    private static final String Request_Param_Incorrect = "请求参数不正确";
    @Autowired
    private HandlerFactory handlerFactory;
    @Autowired
    private CheckProperties checkProperties;

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
            XBean xBean = AnnotationConfigApplicationContext.getXBean(check);
            if (xBean == null) {
                throw new RuntimeException("未配置扫描校验对象,请在XCheckContext中配置ControllerPackage路径");
            }
            return dispatch(xBean, method, joinPoint);
        }
        return XResult.success();
    }

    /**
     * 分配校验对象验证参数
     * @param xBean
     * @param method
     * @param joinPoint
     * @return
     */
    private XResult dispatch(XBean xBean, Method method, ProceedingJoinPoint joinPoint) {
        // 参数准备
        Map<String, String[]> requestParams = prepareRequestParams(xBean, method, joinPoint);
        List<CheckItem> checkItems = xBean.getCheckItems();
        // 遍历表达式
        for (CheckItem checkItem : checkItems) {
            ValidationHandler handler = handlerFactory.getCheckHandler(checkItem);
            XResult validate = handler.validate(xBean, checkItem, requestParams);
            if (validate.isNotPass()) {
                return getResultWithProcessingFailureMessage(checkItem, validate);
            }
        }
        return XResult.success();
    }

    /**
     * 处理提示错误信息
     * @param checkItem
     * @param validate
     * @return
     */
    private XResult getResultWithProcessingFailureMessage(CheckItem checkItem, XResult validate) {
        if (checkItem.getMessage() != null) {
            validate.setMessage(checkItem.getMessage());
            return validate;
        }
        if (!checkProperties.isShowErrorMessage()) {
            validate.setMessage(Request_Param_Incorrect);
        }
        return validate;
    }
    /**
     * 准备校验参数数据
     * @param xBean
     * @param method
     * @param joinPoint
     * @return
     */
    private Map<String, String[]> prepareRequestParams(XBean xBean, Method method, ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = WebUtil.getCurrentRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String[]> newParameterMap = new HashMap<>();
        parameterMap.entrySet().stream().forEach(entry -> {
            String k = entry.getKey();
            // 如果是左中括号开头
            if (k.contains(Square_Brackets)) {
                newParameterMap.put(k.replaceAll("\\[\\d+]", ""), entry.getValue());
            } else {
                newParameterMap.put(k, entry.getValue());
            }
        });

        // 如果包含url path variable参数
        if (xBean.hasPathParam()) {
            Map<String, String> pathVariableMap = (Map<String, String>) request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
            pathVariableMap.entrySet().stream().forEach(
                    entry -> newParameterMap.put(entry.getKey(), new String[]{entry.getValue()})
            );
        }

        // 如果JSON request body传参，遍历找到RequestBody参数再解析
        if (isParameterJSONFormat(request)) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    if (parameterAnnotations[i][j] instanceof RequestBody) {
                        Object jsonParam = joinPoint.getArgs()[i];
                        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(jsonParam);
                        jsonObject.entrySet().stream().forEach(entry -> {
                            put(newParameterMap, entry.getKey(), entry.getValue());
                        });

                    }
                }
            }

        }
        return newParameterMap;
    }

    /**
     * 存放JSON属性的值到新的map中
     * @param map
     * @param key
     * @param value
     */
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
                // 字段不存在时，添加key、value
                map.put(key, new String[]{v});
            } else {
                // 否则在value基础上新增值
                add(map, key, strings, v);
            }
        }
    }

    /**
     * 扩展map的value数组长度+1，有时间可以优化
     * @param map
     * @param key
     * @param strings
     * @param append
     */
    private void add(Map<String, String[]> map, String key, String[] strings, String append) {
        String[] s = new String[strings.length+1];
        for (int i = 0; i < s.length-1; i++) {
            s[i] = strings[i];
        }
        s[s.length-1] = append;
        map.put(key, s);
    }

    /**
     * 是否JSON参数
     * @param request
     * @return
     */
    private boolean isParameterJSONFormat(HttpServletRequest request) {
        String contentType = request.getHeader("content-type");
        return contentType != null && contentType.contains(Application_JSON) ? true : false;
    }

}
