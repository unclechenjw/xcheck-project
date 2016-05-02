package com.cmy.xcheck.util.jy;

import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.XCheckItemSimple;
import com.cmy.xcheck.util.jy.impl.ValidationSimpleHandler;

/**
 * Created by Kevin72c on 2016/5/2.
 */
public class XFactory {

    public static ValidationHandler getCheckHandler(XCheckItem checkItem) {
        ValidationHandler handler;
        if (checkItem instanceof XCheckItemSimple) {
            //  普通校验项目
            handler = ValidationSimpleHandler.INSTANCE;
        } else if (checkItem instanceof  XCheckItemSimple) {
            handler = ValidationSimpleHandler.INSTANCE;
        } else {
            handler = ValidationSimpleHandler.INSTANCE;
        }
        return handler;
    }
}
