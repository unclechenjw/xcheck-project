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
    public void setRequire(boolean require) {
        this.require = require;
    }
    public Map<String, String> getFieldAlias() {
        return fieldAlias;
    }
    public void setFieldAlias(Map<String, String> fieldAlias) {
        this.fieldAlias = fieldAlias;
    }
    public CheckItem[] getCheckItems() {
        return checkItems;
    }
    public void setCheckItems(CheckItem[] checkItems) {
        this.checkItems = checkItems;
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
        private ExpressionTypeEnum expreeionType;
        private boolean nullable;
        public CheckItem(String formula, String[] fields, String message, ExpressionTypeEnum expreeionType,
                boolean nullable) {
            super();
            this.formula = formula;
            this.fields = fields;
            this.message = message;
            this.expreeionType = expreeionType;
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
        public ExpressionTypeEnum getExpreeionType() {
            return expreeionType;
        }
        public void setExpreeionType(ExpressionTypeEnum expreeionType) {
            this.expreeionType = expreeionType;
        }
        public boolean isNullable() {
            return nullable;
        }
        public void setNullable(boolean nullable) {
            this.nullable = nullable;
        }
        
    }
}
