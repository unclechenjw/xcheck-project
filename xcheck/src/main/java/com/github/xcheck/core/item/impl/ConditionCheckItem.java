package com.github.xcheck.core.item.impl;

import com.github.xcheck.core.item.CheckItem;

/**
 * 条件表达式
 * @author chenjw
 * @date 2016/5/2
 **/
public class ConditionCheckItem implements CheckItem {

    private CheckItem firstItem;
    private CheckItem secondItem;
    private CheckItem thirdItem;
    private String message;

    public ConditionCheckItem(CheckItem firstItem, CheckItem secondItem, CheckItem thirdItem, String message) {
        this.firstItem = firstItem;
        this.secondItem = secondItem;
        this.thirdItem = thirdItem;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public CheckItem getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(CheckItem firstItem) {
        this.firstItem = firstItem;
    }

    public CheckItem getSecondItem() {
        return secondItem;
    }

    public void setSecondItem(CheckItem secondItem) {
        this.secondItem = secondItem;
    }

    public CheckItem getThirdItem() {
        return thirdItem;
    }

    public void setThirdItem(CheckItem thirdItem) {
        this.thirdItem = thirdItem;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}