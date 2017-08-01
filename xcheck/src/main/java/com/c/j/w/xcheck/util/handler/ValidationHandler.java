package com.c.j.w.xcheck.util.handler;

import com.c.j.w.xcheck.util.item.XCheckItem;
import com.c.j.w.xcheck.support.XBean;
import com.c.j.w.xcheck.support.XResult;
import com.c.j.w.xcheck.util.item.XCheckItem;

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
