package com.cmy.xcheck.config;

import com.cmy.xcheck.support.XBean;

import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;

public class XMessageBuilder {

    /** 是否输出错误信息 */
    private static final boolean ErrorDisplay = true;
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            InputStreamReader in = new InputStreamReader(XMessageBuilder.class.getClassLoader().getResourceAsStream("com/cmy/xcheck/config/check_messages_CN.properties"), "utf-8");
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String buildMsg(String field, XBean xBean,
                                  XBean.FormulaItem formulaItem, XBean.CheckItem checkItem) {
        if (!xBean.isHint() && !ErrorDisplay) {
            return getProperty("ParameterError");
        }
        if (checkItem.getMessage() != null) {
            return checkItem.getMessage();
        } else {
            String baseMsg = getProperty(formulaItem.getMethodAbbr());
            String filedAlias = getFiledAlias(field, xBean.getFieldAlias());
            return filedAlias + MessageFormat.format(baseMsg, formulaItem.getArgument());
        }
    }

    public static String buildMsg(String field, String methodAbbr, XBean xBean,
                                  XBean.CheckItem checkItem) {
        if (!xBean.isHint() && !ErrorDisplay) {
            return getProperty("ParameterError");
        }
        if (checkItem.getMessage() != null) {
            return checkItem.getMessage();
        } else {
            String baseMsg = getProperty(methodAbbr);
            String filedAlias = getFiledAlias(field, xBean.getFieldAlias());
            return filedAlias + baseMsg;
        }
    }

    private static String getFiledAlias(String field, Map<String, String> fieldAliasMap) {
        if (fieldAliasMap != null) {
            String fieldAlias = fieldAliasMap.get(field);
            return fieldAlias != null ? fieldAlias : field;
        } else {
            return field;
        }
    }

    public static String getProperty(String key) {
        return properties.get(key).toString();
    }


    public static void main(String[] args) {
        System.out.println(properties);
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        Iterator<Map.Entry<Object, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> next = iterator.next();
            System.out.println(next.getKey() + "___" + next.getValue());
        }
        System.out.println(properties.get("!="));
    }


}
