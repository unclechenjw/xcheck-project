package com.c.j.w.xcheck.core.item.impl;

import com.c.j.w.xcheck.core.item.XCheckItem;
import com.c.j.w.xcheck.core.validate.ValidatePack;

import java.util.List;

/**
 * 普通表达式实体
 * Created by Kevin72c on 2016/5/2.
 */
public class XCheckItemSimple implements XCheckItem {
    private List<ValidatePack> validatePacks;
    private List<String> fields;
    private String message;
    private boolean nullable;

    public XCheckItemSimple(List<ValidatePack> validatePacks, final List<String> fields, String message, boolean nullable) {
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