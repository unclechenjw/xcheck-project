package com.cmy.xcheck.util;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.jy.ValidationHandler;
import com.cmy.xcheck.util.jy.XFactory;

import java.util.List;
import java.util.Map;

/**
 * 校验器\回话校验执行器调取
 * Created by Kevin72c on 2016/5/1.
 */
public class XCheckDispatcher {

    public static void execute(XBean xBean, Map<String, String[]> requestParam, XResult xResult) {
        if (xBean.isRequire()) {

        }
        List<XCheckItem> checkItems = xBean.getCheckItems();
        // 遍历表达式
        for (XCheckItem checkItem : checkItems) {
            ValidationHandler handler = XFactory.getCheckHandler(checkItem);
            handler.validate(xBean, checkItem, xResult, requestParam);
            if (xResult.isNotPass()) {
                return;
            }
        }
    }


}
