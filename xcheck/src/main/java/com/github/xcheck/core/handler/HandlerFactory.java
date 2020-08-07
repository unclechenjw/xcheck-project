package com.github.xcheck.core.handler;

import com.github.xcheck.core.handler.impl.ConditionValidationHandlerImpl;
import com.github.xcheck.core.handler.impl.LogicValidationHandlerImpl;
import com.github.xcheck.core.handler.impl.SimpleValidationHandlerImpl;
import com.github.xcheck.core.item.CheckItem;
import com.github.xcheck.core.item.impl.LogicCheckItem;
import com.github.xcheck.core.item.impl.SimpleCheckItem;
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

    public ValidationHandler getCheckHandler(CheckItem checkItem) {
        ValidationHandler handler;
        if (checkItem instanceof SimpleCheckItem) {
            //  普通校验项目
            handler = simpleValidationHandler;
        } else if (checkItem instanceof LogicCheckItem) {
            handler = logicValidationHandler;
        } else {
            handler = conditionValidationHandler;
        }
        return handler;
    }
}
