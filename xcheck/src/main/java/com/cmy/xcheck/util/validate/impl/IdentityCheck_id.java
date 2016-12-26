package com.cmy.xcheck.util.validate.impl;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.StringUtil;
import com.cmy.xcheck.util.validate.ValidateMethod;
import com.cmy.xcheck.util.validate.ValidateParam;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证校验
 * @Author chenjw
 * @Date 2016年12月26日
 */
@Component
public class IdentityCheck_id implements ValidateMethod {

    @Override
    public XResult validate(ValidateParam validateParam) {
        if (validateParam.getArgumentsVal() == null ||
                "CN".equals(validateParam.getArgumentsVal())) {
           return validateCNIdCardNo(validateParam);
        } else {
            return XResult.success();
        }
    }

    @Override
    public String getMethodAttr() {
        return "in";
    }


    /**
     * 功能：身份证的有效验证
     *
     * @param validateParam 身份证号
     * @return 有效：返回true 无效：返回false
     */
    private XResult validateCNIdCardNo(ValidateParam validateParam) {
        String idCardNo = validateParam.getMainFieldVal();
        if (idCardNo == null) {
            return XResult.failure("身份证号码不能为空");
        }
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        String Ai = "";

        // ================ 号码的长度 15位或18位 ================
        if (idCardNo.length() != 15 && idCardNo.length() != 18) {
            return XResult.failure("身份证号码长度应该为15位或18位。" + idCardNo + "填写不正确");
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (idCardNo.length() == 18) {
            Ai = idCardNo.substring(0, 17);
        } else if (idCardNo.length() == 15) {
            Ai = idCardNo.substring(0, 6) + "19" + idCardNo.substring(6, 15);
        }
        if (StringUtil.isAllDigit(Ai) == false) {
            return XResult.failure("身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。" + idCardNo + "填写不正确");
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            return XResult.failure("身份证生日无效。" + idCardNo + "填写不正确");
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                return XResult.failure("身份证生日不在有效范围，" + idCardNo + "填写不正确");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            return XResult.failure("身份证月份无效，" + idCardNo + "填写不正确");
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            return XResult.failure("身份证日期无效，" + idCardNo + "填写不正确");
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        if (CityMap.get(Ai.substring(0, 2)) == null) {
            return XResult.failure("身份证地区编码错误，" + idCardNo + "填写不正确");
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalMulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalMulAiWi = TotalMulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalMulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (idCardNo.length() == 18) {
            if (Ai.equals(idCardNo) == false) {
                return XResult.failure("身份证无效，不是合法的身份证号码，验证码错误。" + idCardNo + "填写不正确");
            }
        }

        return XResult.success();
    }

    private static final Pattern ID_Date_Match_Pattern = Pattern
            .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");

    /**
     * 功能：判断字符串是否为日期格式
     */
    public static boolean isDate(String strDate) {
        Matcher m = ID_Date_Match_Pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     */
    private static final Map<String, String> CityMap = new HashMap<>();

    static {
        CityMap.put("11", "北京");
        CityMap.put("12", "天津");
        CityMap.put("13", "河北");
        CityMap.put("14", "山西");
        CityMap.put("15", "内蒙古");
        CityMap.put("21", "辽宁");
        CityMap.put("22", "吉林");
        CityMap.put("23", "黑龙江");
        CityMap.put("31", "上海");
        CityMap.put("32", "江苏");
        CityMap.put("33", "浙江");
        CityMap.put("34", "安徽");
        CityMap.put("35", "福建");
        CityMap.put("36", "江西");
        CityMap.put("37", "山东");
        CityMap.put("41", "河南");
        CityMap.put("42", "湖北");
        CityMap.put("43", "湖南");
        CityMap.put("44", "广东");
        CityMap.put("45", "广西");
        CityMap.put("46", "海南");
        CityMap.put("50", "重庆");
        CityMap.put("51", "四川");
        CityMap.put("52", "贵州");
        CityMap.put("53", "云南");
        CityMap.put("54", "西藏");
        CityMap.put("61", "陕西");
        CityMap.put("62", "甘肃");
        CityMap.put("63", "青海");
        CityMap.put("64", "宁夏");
        CityMap.put("65", "新疆");
        CityMap.put("71", "台湾");
        CityMap.put("81", "香港");
        CityMap.put("82", "澳门");
        CityMap.put("91", "国外");
    }
}
