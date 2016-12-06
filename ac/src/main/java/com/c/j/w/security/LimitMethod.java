package com.c.j.w.security;

public enum LimitMethod {

    IP(0, "IP访问频率限制"),
    Tel(1, "手机号码限制");

    public int code;
    public String name;

    LimitMethod(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
