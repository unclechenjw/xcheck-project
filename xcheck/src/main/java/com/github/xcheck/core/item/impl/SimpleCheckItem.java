package com.github.xcheck.core.item.impl;

import com.github.xcheck.core.item.CheckItem;
import com.github.xcheck.core.validate.ValidatePack;

import java.util.List;

/**
 * 普通表达式实体
 * @author chenjw
 * @date 2016/5/2
 **/
public class SimpleCheckItem implements CheckItem {
    private List<ValidatePack> validatePacks;
    private List<String> fields;
    private String message;
    private boolean nullable;

    public SimpleCheckItem(List<ValidatePack> validatePacks, final List<String> fields, String message, boolean nullable) {
        this.validatePacks = validatePacks;
        this.fields = fields;
        this.message = message;
        this.nullable = nullable;
    }

    public List<String> getFields() {
        return fields;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isNullable() {
        return nullable;
    }

    public List<ValidatePack> getValidatePacks() {
        return validatePacks;
    }

    public void setValidatePacks(List<ValidatePack> validatePacks) {
        this.validatePacks = validatePacks;
    }
}