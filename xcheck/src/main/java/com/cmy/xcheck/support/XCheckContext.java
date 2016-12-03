package com.cmy.xcheck.support;

import java.util.Locale;

/**
 * 杭州动享互联网技术有限公司
 * @Author chenjw
 * @Date 2016年05月09日
 */
public class XCheckContext {

    /** 扫描包目录 */
    private String[] controllerPackage;
    /** 错误信息是否提示 */
    private boolean errorMessageDisplay = true;
    /** 语言环境 */
    private Locale locale;
    /** 全局字段别名 */
    private String filedAlias;

    public boolean isErrorMessageDisplay() {
        return errorMessageDisplay;
    }

    public void setErrorMessageDisplay(boolean errorMessageDisplay) {
        this.errorMessageDisplay = errorMessageDisplay;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String[] getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String[] controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getFiledAlias() {
        return filedAlias;
    }

    public void setFiledAlias(String filedAlias) {
        this.filedAlias = filedAlias;
    }
}
