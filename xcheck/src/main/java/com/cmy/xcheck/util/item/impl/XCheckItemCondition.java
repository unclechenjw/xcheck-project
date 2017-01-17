package com.cmy.xcheck.util.item.impl;

import com.cmy.xcheck.util.item.XCheckItem;

/**
 * 逻辑表达式
 * Created by Kevin72c on 2017/1/17.
 */
public class XCheckItemCondition implements XCheckItem {

    private XCheckItem firstItem;
    private XCheckItem secondItem;
    private XCheckItem thirdItem;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public XCheckItem getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(XCheckItem firstItem) {
        this.firstItem = firstItem;
    }

    public XCheckItem getSecondItem() {
        return secondItem;
    }

    public void setSecondItem(XCheckItem secondItem) {
        this.secondItem = secondItem;
    }

    public XCheckItem getThirdItem() {
        return thirdItem;
    }

    public void setThirdItem(XCheckItem thirdItem) {
        this.thirdItem = thirdItem;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}