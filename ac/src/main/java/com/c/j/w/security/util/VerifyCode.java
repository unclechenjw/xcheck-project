package com.c.j.w.security.util;

/**
 * @author xuzhen
 * @module 验证码
 * @company 杭州动享互联网技术有限公司
 * @date: 2016/12/7 13:09
 */
public class VerifyCode {

    private String base64Str;
    private String code;

    public String getBase64Str() {
        return base64Str;
    }

    public void setBase64Str(String base64Str) {
        this.base64Str = base64Str;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "VerifyCode{" +
                "base64Str='" + base64Str + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
