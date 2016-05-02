package com.cmy.xcheck.support;

import com.cmy.xcheck.util.item.XCheckItem;

import java.util.List;
import java.util.Map;

public class XBean {

    private Map<String, String> fieldAlias;
    private List<XCheckItem> checkItems;
    private boolean require;
    private boolean hint;
    
    public XBean(Map<String, String> fieldAlias, List<XCheckItem> checkItems, boolean require, boolean hint) {
        this.fieldAlias = fieldAlias;
        this.checkItems = checkItems;
        this.require = require;
        this.hint = hint;
    }

    public boolean isRequire() {
        return require;
    }
    public Map<String, String> getFieldAlias() {
        return fieldAlias;
    }
    public List<XCheckItem> getCheckItems() {
        return checkItems;
    }
    public boolean isHint() {
        return hint;
    }

}