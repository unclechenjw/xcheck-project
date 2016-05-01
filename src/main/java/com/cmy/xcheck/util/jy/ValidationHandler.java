package com.cmy.xcheck.util.jy;

import java.util.Map;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;

public interface ValidationHandler {

    /**
     * 校验方法接口
     * @param requestParam  请求参数参数
     * @param xbean 校验公式
     * @param cr    返回结果
     */
    void validate(Map<String, String[]> requestParam, XBean xbean,
            XResult cr);
}
