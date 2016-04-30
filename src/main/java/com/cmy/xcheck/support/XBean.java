package com.cmy.xcheck.support;

import java.util.Map;

import com.cmy.xcheck.ExpressionTypeEnum;

public class XBean {

    private Map<String, String> fieldAlias;
    private CheckItem[] checkItems;
    private boolean require;
    private boolean hint;
    
    public XBean(Map<String, String> fieldAlias, CheckItem[] checkItems, boolean require,
            boolean hint) {
        super();
        this.fieldAlias = fieldAlias;
        this.checkItems = checkItems;
        this.require = require;
        this.setHint(hint);
    }

    public boolean isRequire() {
        return require;
    }
    public Map<String, String> getFieldAlias() {
        return fieldAlias;
    }
    public CheckItem[] getCheckItems() {
        return checkItems;
    }
    public boolean isHint() {
        return hint;
    }
    public void setHint(boolean hint) {
        this.hint = hint;
    }

    public static class CheckItem {
        private String formula;
        private String[] fields;
        private String message;
        private ExpressionTypeEnum expressionType;
        private boolean nullable;
        public CheckItem(String formula, String[] fields, String message, ExpressionTypeEnum expressionType,
                boolean nullable) {
            super();
            this.formula = formula;
            this.fields = fields;
            this.message = message;
            this.expressionType = expressionType;
            this.nullable = nullable;
        }
        public String getFormula() {
            return formula;
        }
        public void setFormula(String formula) {
            this.formula = formula;
        }
        public String[] getFields() {
            return fields;
        }
        public void setFields(String[] fields) {
            this.fields = fields;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public ExpressionTypeEnum getExpressionType() {
            return expressionType;
        }
        public void setExpressionType(ExpressionTypeEnum expressionType) {
            this.expressionType = expressionType;
        }
        public boolean isNullable() {
            return nullable;
        }
        public boolean canNotBeNull() {
            return !nullable;
        }
        public void setNullable(boolean nullable) {
            this.nullable = nullable;
        }
        
    }

    public static class Builder {
        private Map<String, String> fieldAlias;
        private CheckItem[] checkItems;
        private boolean require;
        private boolean hint;
        public Builder fieldAlias(Map<String, String> fieldAlias) {
            this.fieldAlias = fieldAlias;
            return this;
        }
        public Builder checkItems(CheckItem[] checkItems) {
            this.checkItems = checkItems;
            return this;
        }
        public Builder require(boolean require) {
            this.require = require;
            return this;
        }
        public Builder hint(boolean hint) {
            this.hint = hint;
            return this;
        }
        public XBean build() { // 构建，返回一个新对象
            return new XBean(this);
        }
    }

    public XBean(Builder builder) {
        fieldAlias = builder.fieldAlias;
        checkItems = builder.checkItems;
        require    = builder.require;
        hint       = builder.hint;
    }
}
