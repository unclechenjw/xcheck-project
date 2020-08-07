package com.github.xcheck.core.handler;

import com.github.xcheck.core.item.XCheckItem;
import com.github.xcheck.core.XBean;
import com.github.xcheck.core.XResult;

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
