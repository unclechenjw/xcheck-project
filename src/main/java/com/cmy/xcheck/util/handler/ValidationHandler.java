package com.cmy.xcheck.util.handler;

import java.util.Map;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.support.XResult;

public interface ValidationHandler {

    /**
     * 校验方法接口
     * @param xBean
     * @param checkItem
     * @param xResult
     * @param requestParam
     */
    void validate(XBean xBean, XCheckItem checkItem, XResult xResult, Map<String, String[]> requestParam);
}
