package com.c.j.w.xcheck.core.handler;

import com.c.j.w.xcheck.core.item.XCheckItem;
import com.c.j.w.xcheck.core.XBean;
import com.c.j.w.xcheck.core.XResult;

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
