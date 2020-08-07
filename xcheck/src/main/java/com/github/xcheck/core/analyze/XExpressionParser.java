package com.github.xcheck.core.analyze;

import com.github.xcheck.core.XAnnotationConfigApplicationContext;
import com.github.xcheck.core.XBean;
import com.github.xcheck.support.annotation.Check;
import com.github.xcheck.core.util.StringUtil;
import com.github.xcheck.core.analyze.impl.ConditionExpressionAnalyzer;
import com.github.xcheck.core.analyze.impl.LogicExpressionAnalyzer;
import com.github.xcheck.core.analyze.impl.SimpleExpressionAnalyzer;
import com.github.xcheck.core.item.XCheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 表达式解析
 * Created by Kevin72c on 2016/4/29.
 */
@Component
@Scope("prototype")
public class XExpressionParser {

    @Autowired
    private SimpleExpressionAnalyzer simpleExpressionAnalyzer;
    @Autowired
    private ConditionExpressionAnalyzer conditionExpressionAnalyzer;
    @Autowired
    private LogicExpressionAnalyzer logicExpressionAnalyzer;
    /**
     * 扫描解析校验对象
     * @param classes
     */
    public void parseXBean(Set<Class<?>> classes) {
        for (Class<?> clz : classes) {
            Method[] declaredMethods = clz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(Check.class)) {
                    parseXBean_(method);
                }
            }
        }
    }

    /**
     * 解析校验对象
     * @param method
     */
    public void parseXBean_(Method method) {
        Check check = method.getAnnotation(Check.class);
        String[] values = check.value();
        Map<String, String> fieldAlias = parseFieldAliasToMap(check.fieldAlias());

        List<XCheckItem> checkItems = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (StringUtil.isNotEmpty(values[i])) {
                checkItems.add(parseExpression(values[i]));
            }
        }

        String[] urls = getUrls(method);
        boolean hasPathParam = false;
        for (String url :urls) {
            if (url.contains("{")) {
                hasPathParam = true;
            }
        }
        XBean xBean = new XBean(fieldAlias, checkItems, hasPathParam, urls);

        // 注册校验对象
        XAnnotationConfigApplicationContext.register(check, xBean);
    }

    private String[] getUrls(Method method) {
        String[] urls;
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            urls = requestMapping.value();
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping requestMapping = method.getAnnotation(GetMapping.class);
            urls = requestMapping.value();
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping requestMapping = method.getAnnotation(PostMapping.class);
            urls = requestMapping.value();
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping requestMapping = method.getAnnotation(DeleteMapping.class);
            urls = requestMapping.value();
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping requestMapping = method.getAnnotation(PutMapping.class);
            urls = requestMapping.value();
        } else if (method.isAnnotationPresent(PatchMapping.class)) {
            PatchMapping requestMapping = method.getAnnotation(PatchMapping.class);
            urls = requestMapping.value();
        } else {
            throw new IllegalStateException("尚未实现的Mapping Url分析");
        }
        return urls;
    }

    /**
     * 字段别名转map
     * @param fieldAlias
     * @return
     */
    private Map<String, String> parseFieldAliasToMap(String[] fieldAlias) {
        Map<String, String> m = new HashMap<>();
        for (String alias : fieldAlias) {
            String[] split = alias.replaceAll("\\s", "").split(",");
            for (String sp :split) {
                if(StringUtil.isEmpty(sp)) {
                    continue;
                }
                String[] fieldAndName = sp.split("=");
                int fieldNameLen = fieldAndName.length;
                if (fieldNameLen == 2) {
                    m.put(fieldAndName[0], fieldAndName[1]);
                } else {
                    throw new IllegalArgumentException("字段别名设置不正确");
                }
            }
        }
        return m;
    }

    /**
     * 解析表达式类型
     * @param expression
     * @return
     */
    public XCheckItem parseExpression(String expression) {
        XCheckItem checkItem;
        expression = trimExpression(expression);
        if (expression.startsWith("if")) {
            // if表达式
            checkItem = conditionExpressionAnalyzer.analyze(expression);
        } else if (expression.matches("(.*?)(<=|<|>=|>|==|!=)(.*)")) {
            // 逻辑比较表达式
            checkItem = logicExpressionAnalyzer.analyze(expression);
        } else {
            // 普通表达式
            checkItem = simpleExpressionAnalyzer.analyze(expression);
        }
        return checkItem;
    }

    private String trimExpression(String expression) {
        int colonIndex = expression.indexOf(":");
        if (colonIndex == -1) {
            return expression.replace(" ", "");
        } else {
            return expression.substring(0, colonIndex).replace(" ", "") +
                    expression.substring(colonIndex, expression.length());
        }
    }
}
