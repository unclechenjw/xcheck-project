package com.c.j.w.xcheck.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author chenjw
 * @Date 2016年05月09日
 */
@Configuration
@ConfigurationProperties(prefix = "xcheck")
public class XCheckProperties {

    /** 扫描包目录 */
    private String[] controllerPackage;
    /** 错误信息是否提示 */
    private boolean showErrorMessage = true;
    /** 语言环境 */
//    private Locale locale;
    /** 全局字段别名 */
    private String columnAlias;

    public String[] getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String... controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getColumnAlias() {
        return columnAlias;
    }

    public void setColumnAlias(String columnAlias) {
        this.columnAlias = columnAlias;
    }

    public boolean isShowErrorMessage() {
        return showErrorMessage;
    }

    public void setShowErrorMessage(boolean showErrorMessage) {
        this.showErrorMessage = showErrorMessage;
    }
}
