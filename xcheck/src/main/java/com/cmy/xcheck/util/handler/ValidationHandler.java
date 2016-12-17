package com.cmy.xcheck.util.handler;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.item.XCheckItem;

import javax.servlet.http.HttpServletRequest;

public interface ValidationHandler {

    /**
     * 校验方法接口
     * @param xBean
     * @param checkItem
     * @param request
     */
    XResult validate(XBean xBean, XCheckItem checkItem, HttpServletRequest request);
}
