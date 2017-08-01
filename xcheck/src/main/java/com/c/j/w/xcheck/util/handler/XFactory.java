package com.c.j.w.xcheck.util.handler;

import com.c.j.w.xcheck.util.handler.impl.ConditionValidationHandlerImpl;
import com.c.j.w.xcheck.util.handler.impl.LogicValidationHandlerImpl;
import com.c.j.w.xcheck.util.handler.impl.SimpleValidationHandlerImpl;
import com.c.j.w.xcheck.util.item.XCheckItem;
import com.c.j.w.xcheck.util.item.impl.XCheckItemLogic;
import com.c.j.w.xcheck.util.item.impl.XCheckItemSimple;
import com.c.j.w.xcheck.util.item.XCheckItem;
import com.c.j.w.xcheck.util.item.impl.XCheckItemLogic;
import com.c.j.w.xcheck.util.item.impl.XCheckItemSimple;
import com.c.j.w.xcheck.util.handler.impl.ConditionValidationHandlerImpl;
import com.c.j.w.xcheck.util.handler.impl.LogicValidationHandlerImpl;
import com.c.j.w.xcheck.util.handler.impl.SimpleValidationHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理器工厂
 * Created by Kevin72c on 2016/5/2.
 */
@Component
public class XFactory {

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
