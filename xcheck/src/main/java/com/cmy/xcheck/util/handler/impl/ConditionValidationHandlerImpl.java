package com.cmy.xcheck.util.handler.impl;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.handler.XFactory;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.impl.XCheckItemCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConditionValidationHandlerImpl implements ValidationHandler {

    @Autowired
    private XFactory xFactory;

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

