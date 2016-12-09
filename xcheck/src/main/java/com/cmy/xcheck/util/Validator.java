package com.cmy.xcheck.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @module 校验处理类
 * @author chenjw
 * @date: 2016/9/21 10:53
 */
public enum Validator {
    
    INSTANCE;

    private static final HashMap<String, CheckMethod> CHECK_METHODS = new HashMap<>();
    private static final Pattern TEL_PATTERN =
            Pattern.compile("^\\d{11}$");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^.+?@.+?\\..+$");
    private static final Pattern MONEY_FORMAT_PATTERN =
            Pattern.compile("^-?\\d+(\\.\\d{1,2})?$");
    private static final Pattern DECIMAL_PATTERN =
            Pattern.compile("^-?\\d+(\\.\\d*)?$");
    
    static {
        initCheckMethods();
    }
    
    private static void initCheckMethods() {
        try {
            Class<? extends Validator> INSTANCEClass = INSTANCE.getClass();
            Method isAllLetter    = INSTANCEClass.getMethod("isAllLetter", String.class);
            Method isAllNotLetter = INSTANCEClass.getMethod("isNotAllLetter", String.class);
            Method isAllDigit     = INSTANCEClass.getMethod("isAllDigit", String.class);
            Method isAllNotDigit  = INSTANCEClass.getMethod("isNotAllDigit", String.class);
            Method isLengthIn     = INSTANCEClass.getMethod("isLengthIn", String.class, String.class);
            Method isInMaxLength  = INSTANCEClass.getMethod("isInMaxLength", String.class, String.class);
            Method isPhoneNumber  = INSTANCEClass.getMethod("isPhoneNumber", String.class);
            Method isEmail        = INSTANCEClass.getMethod("isEmail", String.class);
            Method isMoneyFormat  = INSTANCEClass.getMethod("isMoneyFormat", String.class);
            Method isDecimal      = INSTANCEClass.getMethod("isDecimal", String.class);
            Method in             = INSTANCEClass.getMethod("in", String.class, String.class);
            Method reg            = INSTANCEClass.getMethod("reg", String.class, String.class);
            Method validIdentity  = INSTANCEClass.getMethod("validIdentity", String.class);

            CHECK_METHODS.put("w",  new CheckMethod(isAllLetter, 0));      // 纯字母
            CHECK_METHODS.put("W",  new CheckMethod(isAllNotLetter, 0));   // 非字母
            CHECK_METHODS.put("d",  new CheckMethod(isAllDigit, 0));       // 纯数字
            CHECK_METHODS.put("D",  new CheckMethod(isAllNotDigit, 0));    // 非数字
            CHECK_METHODS.put("l",  new CheckMethod(isLengthIn, 1));       // 限定为指定长度
            CHECK_METHODS.put("ml", new CheckMethod(isInMaxLength, 1));    // 限定最大长度
            CHECK_METHODS.put("p",  new CheckMethod(isPhoneNumber, 0));    // 手机号码格式
            CHECK_METHODS.put("e",  new CheckMethod(isEmail, 0));          // 邮箱格式
            CHECK_METHODS.put("$",  new CheckMethod(isMoneyFormat, 0));    // 金额格式
            CHECK_METHODS.put("in", new CheckMethod(in, 1));               // 参数为制定范围
            CHECK_METHODS.put("reg", new CheckMethod(reg, 1));             // 正则校验
            CHECK_METHODS.put("decimal", new CheckMethod(isDecimal, 0));   // 数值
            CHECK_METHODS.put("id", new CheckMethod(validIdentity, 0));    // 身份证

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
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
    
    public static boolean isNotAllLetter(String str) {
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

    public static boolean isNotAllDigit(String str){
        char[] charArray = str.toCharArray();
        if (charArray.length == 0) 
            return false;
        for (char aChar : charArray) {
            if (Character.isDigit(aChar))
                return false;
        }
        return true;
    }

    private boolean isLengthIn1(String str, String len) {
        if(!isAllDigit(len)) {
            throw new IllegalArgumentException();
        }
        return str.length() == Integer.parseInt(len);
    }

    public boolean isLengthIn2(String str, String minLen, String maxLen) {
        int strLen = str.length();
        if (!isAllDigit(minLen) && !isAllDigit(maxLen)) {
            throw new IllegalArgumentException();
        }
        int iminLen = Integer.parseInt(minLen);
        int imaxLen = Integer.parseInt(maxLen);
        return strLen >= iminLen && strLen <= imaxLen;
    }
    
    public boolean isLengthIn(String str, String lens) {
        String[] split = lens.split(",");
        if (split.length == 1 ) {
            return isLengthIn1(str, lens);
        } else if (split.length == 2 ) {
            return isLengthIn2(str, split[0], split[1]);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * str是否在len的长度范围
     * @param str
     * @param len
     * @return
     */
    public boolean isInMaxLength(String str, String len) {
        if (isNotAllDigit(len)) {
            throw new IllegalArgumentException();
        }
        return str.length() <= Integer.parseInt(len);
    }

    /**
     * 是否中国11位手机号码格式
     * @param telNumber
     * @return
     */
    public static boolean isPhoneNumber(String telNumber) {
        Matcher matcher = TEL_PATTERN.matcher(telNumber);
        return matcher.find();
    }

    /**
     * 是否邮箱格式
     * @param eMail
     * @return
     */
    public static boolean isEmail(String eMail) {
        Matcher matcher = EMAIL_PATTERN.matcher(eMail);
        return matcher.find();
    }

    /**
     * 是否标准金额格式 两位小数
     * @param str
     * @return
     */
    public static boolean isMoneyFormat(String str) {
        Matcher matcher = MONEY_FORMAT_PATTERN.matcher(str);
        return matcher.find();
    }

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
    
    public static boolean isNotDecimal(String str) {
        return !isDecimal(str);
    }

    /**
     * value是否再arg范围内
     * @param value
     * @param arg
     * @return
     */
    public static boolean in(String value, String arg) {
        String[] split = arg.split(",");
        for (String e : split) {
            if (value.equals(e)) {
                return true;
            }
        }
        return false;
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

    /**
     * 正则表达式校验
     * @param value
     * @param regEx
     * @return
     */
    public static boolean reg(String value, String regEx) {
        return value.matches(regEx);
    }

    /**
     * 身份证校验
     * @param value
//     * @param countryCode
     * @return
     */
    public static boolean validIdentity(String value) {

        return true;
    }

    public static class CheckMethod {
        private final Method method;
        private final int argNum;
        public Method getMethod() {
            return method;
        }
        public int getArgNum() {
            return argNum;
        }
        private CheckMethod(Method method, int argNum) {
            this.method = method;
            this.argNum = argNum;
        }

    }

    public static CheckMethod getCheckMethod(String methodAbbr) {
        return CHECK_METHODS.get(methodAbbr);
    }

}
