package com.c.j.w.xcheck.core.util;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author chenjw
 * @Date 2016年12月23日
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    public static boolean isEmpty(String[] args) {
        if (args == null || args.length == 0) {
            return true;
        }
        for (String str : args) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 是否纯字母
     * @param str
     * @return
     */
    public static boolean isAllLetter(String str) {
        char[] charArray = str.toCharArray();
        if (charArray.length == 0)
            return false;
        for (char aChar : charArray) {
            if (!Character.isLetter(aChar))
                return false;
        }
        return true;
    }
    public static boolean isAllNotLetter(String str) {
        char[] charArray = str.toCharArray();
        if (charArray.length == 0)
            return false;
        for (char aChar : charArray) {
            if (Character.isLetter(aChar))
                return false;
        }
        return true;
    }


    /**
     * 是否纯数字
     * @param str
     * @return
     */
    public static boolean isAllDigit(String str){
        if (isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        for (char aChar : charArray) {
            if (!Character.isDigit(aChar))
                return false;
        }
        return true;
    }
    public static boolean isAllNotDigit(String str){
        char[] charArray = str.toCharArray();
        if (charArray.length == 0)
            return false;
        for (char aChar : charArray) {
            if (Character.isDigit(aChar))
                return false;
        }
        return true;
    }

    private static final Pattern DECIMAL_PATTERN = Pattern.compile("^-?\\d+(\\.\\d*)?$");
    /**
     * 是否数值 包含负数，小数
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        if (str == null)
            return false;
        Matcher matcher = DECIMAL_PATTERN.matcher(str);
        return matcher.find();
    }
    /**
     * 是否标准年月日
     * @param param
     * @return
     */
    public static boolean isStandardDateFormat(String param) {
        try {
            if (param.indexOf(":") > 0) {
                String strFormat = "yyyy-MM-dd HH:mm:ss";
                if (param.indexOf("/") > 0)
                    strFormat = "yyyy/MM/dd HH:mm:ss";
                SimpleDateFormat formatDateTime = new SimpleDateFormat(strFormat);
                formatDateTime.parse(param);
            } else {
                String strFormat = "yyyy-MM-dd";
                if (param.indexOf("/") > 0)
                    strFormat = "yyyy/MM/dd";
                SimpleDateFormat formatDate = new SimpleDateFormat(strFormat);
                formatDate.parse(param);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
