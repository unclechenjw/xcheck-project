package com.cmy.xcheck.config;

import java.text.MessageFormat;
import java.util.HashMap;

public class ErrorMessageBuilder {

    private static final HashMap<String, String> msg = new HashMap<String, String>();

    static {
        // 输出错误信息使用
        msg.put(">", "必须大于");
        msg.put("<", "必须小于");
        msg.put(">=", "必须大于或等于");
        msg.put("<=", "必须小于或等于");
        msg.put("==", "必须等于");
        msg.put("!=", "必须不等于");
        
        msg.put("d", "必须为数字");
        msg.put("D", "不能包含数字");
        msg.put("w", "必须为字母");
        msg.put("W", "不能包含字母");
        msg.put("e", "邮箱格式不正确");
        msg.put("p", "手机号码不正确");
        msg.put("l", "字符长度必须是{0}位");
        msg.put("ml", "字符长度最大只能{0}位");
        msg.put("$", "金额输入错误");
        msg.put("in", "必须为{0}");

        // 有参数的校验方法在此配置参数个数
//        ARGS_OF_METHOD.put("L", 1);
//        ARGS_OF_METHOD.put("ML", 1);
//        ARGS_OF_METHOD.put("IN", 1);
    }

    public static String getMsg(String key) {
        return msg.get(key);
    }

    public static String buildMsg(String methodAbbr, String field,
            String arguments, String prompt) {
        if (prompt != null) {
            return prompt;
        } else {
            String baseMsg = msg.get(methodAbbr);
            
            return field + MessageFormat.format(baseMsg, arguments);
//            this.errorMsg = getFieldComment(fieldName) + MessageFormat.format(msg, args);
//            return field + msg.get(methodAbbr);
        }
    }

//    public static int getNumOfArgsByMethodName(String key) {
//        return ARGS_OF_METHOD.get(key) == null ? 0 : ARGS_OF_METHOD.get(key);
//    }
}
