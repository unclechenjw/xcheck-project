package com.cmy.xcheck.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    
    private static final Validator INSTANCE = new Validator();
    
    public static final HashMap<String, Item> CHECK_METHODS = 
            new HashMap<String, Item>();
    private static final Pattern TEL_PATT =
            Pattern.compile("^\\d{11}$");
    private static final Pattern EMAIL_PATT =
            Pattern.compile("^.+?@.+?\\..+$");
    private static final Pattern MONEY_FORMAT_PATT =
            Pattern.compile("^\\-?\\d+(\\.\\d{1,2})?$");
    private static final Pattern NUMERICAL_PATT = 
            Pattern.compile("^\\-?\\d+(.\\d*)?$");
    
    static {
        initCheckMethods();
    }
    
    private static void initCheckMethods() {
        try {
            Method isAllLetter    = INSTANCE.getClass().getMethod("isAllLetter", String.class);
            Method isAllNotLetter = INSTANCE.getClass().getMethod("isNotAllLetter", String.class);
            Method isAllDigit     = INSTANCE.getClass().getMethod("isAllDigit", String.class);
            Method isAllNotDigit  = INSTANCE.getClass().getMethod("isNotAllDigit", String.class);
            Method isLengthIn     = INSTANCE.getClass().getMethod("isLengthIn", String.class, String.class);
            Method isInMaxLength  = INSTANCE.getClass().getMethod("isInMaxLength", String.class, String.class);
            Method isPhoneNumber  = INSTANCE.getClass().getMethod("isPhoneNumber", String.class);
            Method isEmail        = INSTANCE.getClass().getMethod("isEmail", String.class);
            Method isMoneyFormat  = INSTANCE.getClass().getMethod("isMoneyFormat", String.class);
            Method in             = INSTANCE.getClass().getMethod("in", String.class, String.class);
            
            CHECK_METHODS.put("w",  new Item(isAllLetter, 0));
            CHECK_METHODS.put("W",  new Item(isAllNotLetter, 0));
            CHECK_METHODS.put("d",  new Item(isAllDigit, 0));
            CHECK_METHODS.put("D",  new Item(isAllNotDigit, 0));
            CHECK_METHODS.put("l",  new Item(isLengthIn, 1));
            CHECK_METHODS.put("ml", new Item(isInMaxLength, 1));
            CHECK_METHODS.put("p",  new Item(isPhoneNumber, 0));
            CHECK_METHODS.put("e",  new Item(isEmail, 0));
            CHECK_METHODS.put("$",  new Item(isMoneyFormat, 0));
            CHECK_METHODS.put("in", new Item(in, 1));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

//    public static boolean isEmpty(Object obj) {
//        boolean ret = false;
//        if (obj == null) {
//            ret = true;
//        }
//        if (obj instanceof String) {
//            ret = isEmpty(obj.toString());
//        } else if (obj instanceof String) {
//            try {
//                ret = isEmpty(Integer.parseInt(obj.toString()));
//            } catch (Exception e) {
//            }
//        }
//        return ret;
//    }
    
    public static boolean isAllLetter(String str) {
        char[] charArray = str.toCharArray();
        if (charArray.length == 0) 
            return false;
        for (int i = 0; i < charArray.length; i++) {
            if (!Character.isLetter(charArray[i]))
                return false;
        }
        return true;
    }
    
    public static boolean isNotAllLetter(String str) {
        char[] charArray = str.toCharArray();
        if (charArray.length == 0) 
            return false;
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i]))
                return false;
        }
        return true;
    }
    
    public static boolean isAllDigit(String str){
        if (isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (!Character.isDigit(charArray[i]))
                return false;
        }
        return true;
    }

    public static boolean isNotAllDigit(String str){
        char[] charArray = str.toCharArray();
        if (charArray.length == 0) 
            return false;
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isDigit(charArray[i]))
                return false;
        }
        return true;
    }

    public boolean isLengthIn1(String str, String len) {
        if(!isAllDigit(len)) {
            throw new IllegalArgumentException();
        }
        return str.length() == Integer.valueOf(len);
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

    public boolean isInMaxLength(String str, String len) {
        if (isNotAllDigit(len)) {
            throw new IllegalArgumentException();
        }
        return str.length() <= Integer.parseInt(len);
    }
    
    public static boolean isPhoneNumber(String telNumber) {
        Matcher matcher = TEL_PATT.matcher(telNumber);
        return matcher.find();
    }

    public static boolean isEmail(String eMail) {
        Matcher matcher = EMAIL_PATT.matcher(eMail);
        return matcher.find();
    }

    public static boolean isMoneyFormat(String str) {
        Matcher matcher = MONEY_FORMAT_PATT.matcher(str);
        return matcher.find();
    }
    
    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        Matcher matcher = NUMERICAL_PATT.matcher(str);
        return matcher.find();
    }
    
    public static boolean isNotNumeric(String str) {
        return !isNumeric(str);
    }

    public static boolean in(String s, String arg) {
        String[] split = arg.split(",");
        for (String e : split) {
            if (s.equals(e)) {
                return true;
            }
        }
        return false;
    }

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
        } catch (Exception exception) {
            return false;
        }
    }

    public static Boolean calculate(String methodAbbr, String val,
            String arguments) throws Exception {
        return CHECK_METHODS.get(methodAbbr).calculate(val, arguments);
    }
    
    private static class Item {
        private final Method method;
        private final int argNum;
        private Item (Method method, int argNum) {
            this.method = method;
            this.argNum = argNum;
        }
        
        public Boolean calculate(String val, String arguments) throws Exception{
            if (argNum == 0) {
                return (Boolean) method.invoke(INSTANCE, val);
            } else {
                return (Boolean) method.invoke(INSTANCE, val, arguments);
            }
        }
    }
}
