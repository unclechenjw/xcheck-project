package com.cmy.xcheck.util.jy;

import java.util.Map;

import com.cmy.xcheck.util.XResult;

public interface ValidationHandler {

    /**
     * 校验方法接口
     * @param source  JSONObject参数
     * @param express 校验公式
     * @param cr      返回结果
     */
    public void validate(Map<String, String> requestParam, String express,
            XResult cr);
}
