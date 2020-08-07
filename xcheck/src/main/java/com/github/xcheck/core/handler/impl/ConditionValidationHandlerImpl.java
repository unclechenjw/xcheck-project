package com.github.xcheck.core.handler.impl;

import com.github.xcheck.core.handler.ValidationHandler;
import com.github.xcheck.core.item.XCheckItem;
import com.github.xcheck.core.XBean;
import com.github.xcheck.core.XResult;
import com.github.xcheck.core.handler.HandlerFactory;
import com.github.xcheck.core.item.impl.XCheckItemCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConditionValidationHandlerImpl implements ValidationHandler {

    @Autowired
    private HandlerFactory xFactory;

    @Override
    public XResult validate(XBean xBean, XCheckItem checkItem, Map<String, String[]> requestParams) {
        XCheckItemCondition xCheckItemCondition = (XCheckItemCondition) checkItem;
        XCheckItem tmpItem = xCheckItemCondition.getFirstItem();
        ValidationHandler handler = xFactory.getCheckHandler(tmpItem);
        XResult firstValidate = handler.validate(xBean, tmpItem, requestParams);
        if (firstValidate.isPass()) {
            tmpItem = xCheckItemCondition.getSecondItem();
            handler = xFactory.getCheckHandler(tmpItem);
            return handler.validate(xBean, tmpItem, requestParams);
        } else {
            tmpItem = xCheckItemCondition.getThirdItem();
            if (tmpItem == null) {
                return XResult.success();
            }
            handler = xFactory.getCheckHandler(tmpItem);
            return handler.validate(xBean, tmpItem, requestParams);
        }
    }

}

