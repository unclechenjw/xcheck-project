package com.cmy.xcheck.util.handler;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.item.XCheckItem;

import java.util.Map;

public interface ValidationHandler {

    /**
     * 校验方法接口
     * @param xBean
     * @param checkItem
     * @param requestParams
     */
    XResult validate(XBean xBean, XCheckItem checkItem, Map<String, String[]> requestParams);
}
