package com.c.j.w.xcheck.core.handler;

import com.c.j.w.xcheck.core.handler.impl.ConditionValidationHandlerImpl;
import com.c.j.w.xcheck.core.handler.impl.LogicValidationHandlerImpl;
import com.c.j.w.xcheck.core.handler.impl.SimpleValidationHandlerImpl;
import com.c.j.w.xcheck.core.item.XCheckItem;
import com.c.j.w.xcheck.core.item.impl.XCheckItemLogic;
import com.c.j.w.xcheck.core.item.impl.XCheckItemSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理器工厂
 * Created by Kevin72c on 2016/5/2.
 */
@Component
public class HandlerFactory {

    @Autowired
    private SimpleValidationHandlerImpl simpleValidationHandler;
    @Autowired
    private LogicValidationHandlerImpl logicValidationHandler;
    @Autowired
    private ConditionValidationHandlerImpl conditionValidationHandler;

    public ValidationHandler getCheckHandler(XCheckItem checkItem) {
        ValidationHandler handler;
        if (checkItem instanceof XCheckItemSimple) {
            //  普通校验项目
            handler = simpleValidationHandler;
        } else if (checkItem instanceof XCheckItemLogic) {
            handler = logicValidationHandler;
        } else {
            handler = conditionValidationHandler;
        }
        return handler;
    }
}
