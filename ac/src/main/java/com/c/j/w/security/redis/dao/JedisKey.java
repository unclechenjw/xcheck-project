package com.c.j.w.security.redis.dao;

/**
 *
 * @Author chenjw
 * @Date 2016年12月06日
 */
public enum JedisKey {

    Security_IP("security:ip:", "安全过滤ip"),
    Security_Tel("security:tel:", "安全过滤手机号码"),
    Security_Access_Frequency("security:access:frequency", "访问频率")
    ;

    String code;
    String name;

    JedisKey(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return code;
    }
}
