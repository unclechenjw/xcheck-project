package com.cmy.xcheck.config;

import com.cmy.xcheck.support.XBean;

import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;

public class XMessageBuilder {


    private static final boolean ErrorDisplay = true;

//    private static final HashMap<String, String> msg = new HashMap<String, String>();

    static {
//        // 输出错误信息使用
//        msg.put(">", "必须大于");
//        msg.put("<", "必须小于");
//        msg.put(">=", "必须大于或等于");
//        msg.put("<=", "必须小于或等于");
//        msg.put("==", "必须等于");
//        msg.put("!=", "必须不等于");
//
//        msg.put("d", "必须为数字");
//        msg.put("D", "不能包含数字");
//        msg.put("w", "必须为字母");
//        msg.put("W", "不能包含字母");
//        msg.put("e", "邮箱格式不正确");
//        msg.put("p", "手机号码不正确");
//        msg.put("l", "字符长度必须是{0}位");
//        msg.put("ml", "字符长度最大只能{0}位");
//        msg.put("$", "金额输入错误");
//        msg.put("in", "必须为{0}");
        init();
    }

//    public static String getMsg(String key) {
//        return msg.get(key);
//    }

    public static String buildMsg(String methodAbbr, String field,
                                  String arguments, XBean xBean, XBean.CheckItem checkItem) {
        if (!xBean.isHint() && !ErrorDisplay) {
            return getProperty("ParameterError");
        }
        if (checkItem.getMessage() != null) {
            return checkItem.getMessage();
        } else {
            String baseMsg = getProperty(methodAbbr);
            String filedAlias = getFiledAlias(field, xBean.getFieldAlias());
            return filedAlias + MessageFormat.format(baseMsg, arguments);
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

    private static Properties properties;
    public static void init() {
        properties = new Properties();
        try {
            InputStreamReader in = new InputStreamReader(XMessageBuilder.class.getClassLoader().getResourceAsStream("com/cmy/xcheck/config/check_messages_CN.properties"), "utf-8");
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
