package com.cmy.xcheck.util.handler;

import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.XCheckItemLogic;
import com.cmy.xcheck.util.item.XCheckItemSimple;
import com.cmy.xcheck.util.handler.impl.ConditionValidationHandlerImpl;
import com.cmy.xcheck.util.handler.impl.LogicValidationHandlerImpl;
import com.cmy.xcheck.util.handler.impl.SimpleValidationHandlerImpl;

/**
 * 处理器工厂
 * Created by Kevin72c on 2016/5/2.
 */
public class XFactory {

    public static ValidationHandler getCheckHandler(XCheckItem checkItem) {
        ValidationHandler handler;
        if (checkItem instanceof XCheckItemSimple) {
            //  普通校验项目
            handler = SimpleValidationHandlerImpl.INSTANCE;
        } else if (checkItem instanceof XCheckItemLogic) {
            handler = LogicValidationHandlerImpl.INSTANCE;
        } else {
            handler = ConditionValidationHandlerImpl.INSTANCE;
        }
        return handler;
    }
}
