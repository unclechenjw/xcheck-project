package com.c.j.w.security.redis.dao;

/**
 * Jedis key值前缀
 * @Author chenjw
 * @Date 2016年12月06日
 */
public enum JedisKey {

    Security_IP("security:ip:", "安全过滤ip"),
    Security_Tel("security:tel:", "安全过滤手机号码"),
    Security_Access_Frequency("security:access:frequency:", "模块访问频率"),
    Security_Code("security:code:", "安全校验码")
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
