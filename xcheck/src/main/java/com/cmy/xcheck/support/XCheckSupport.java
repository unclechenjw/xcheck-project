package com.cmy.xcheck.support;

import com.cmy.xcheck.support.annotation.Check;
import com.cmy.xcheck.support.annotation.XAnnotationConfigApplicationContext;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.handler.XFactory;
import com.cmy.xcheck.util.item.XCheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class XCheckSupport {

    @Autowired(required = false)
    private XCheckHandlerAdapter xCheckHandlerAdapter;
    @Autowired
    private XFactory xFactory;

    /**
     * 校验入口
     * @return
     */
    public XResult check(Method method, HttpServletRequest request) {
        // 判断是否验证对象
        boolean isAnnotationPresent = method.isAnnotationPresent(Check.class);
        // 带有Check注解的方法进行参数规则校验
        if (isAnnotationPresent) {
            Check check = method.getAnnotation(Check.class);
            XBean xBean = XAnnotationConfigApplicationContext.getXBean(check);
            if (xBean == null) {
                throw new RuntimeException("未配置扫描校验对象,请在XCheckContext中配置ControllerPackage路径");
            }
            return dispatch(xBean, request);
        }
        return XResult.success();
    }

    private XResult dispatch(XBean xBean, HttpServletRequest request) {
        if (xBean.isRequire()) {
            if (!xCheckHandlerAdapter.verifySession(request.getParameterMap())) {
                return new XResult(XResult.XCHECK_SESSION_EXPIRE, "用户未登录或会话过期");
            }
        }

        Map<String, String[]> requestParams = prepareRequestParams(request, xBean);
        List<XCheckItem> checkItems = xBean.getCheckItems();
        // 遍历表达式
        for (XCheckItem checkItem : checkItems) {
            ValidationHandler handler = xFactory.getCheckHandler(checkItem);
            XResult validate = handler.validate(xBean, checkItem, requestParams);
            if (validate.isNotPass()) {
                return validate;
            }
        }
        return XResult.success();
    }


    private Map<String, String[]>  prepareRequestParams(HttpServletRequest request, XBean xBean) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String[]> newMap = new HashMap<>();
        parameterMap.entrySet().stream().forEach(entry -> {
            String k = entry.getKey();
            if (k.contains("[")) {
                newMap.put(k.replaceAll("\\[\\d+\\]", ""),
                        entry.getValue());
            } else {
                newMap.put(k, entry.getValue());
            }
        });

        // url path variables analyze
        if (xBean.hasPathParam()) {
            // TODO: 2016/12/18
        }
        return newMap;
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
