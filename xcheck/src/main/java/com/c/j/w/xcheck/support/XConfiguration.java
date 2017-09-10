package com.c.j.w.xcheck.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

/**
 * @Author chenjw
 * @Date 2016年05月09日
 */
@ConfigurationProperties(prefix = "xcheck")
public class XConfiguration {

    /** 扫描包目录 */
    private String[] controllerPackage;
    /** 错误信息是否提示 */
    private boolean errorMessageDisplay;
    /** 语言环境 */
//    private Locale locale;
    /** 全局字段别名 */
    private String filedAlias;

    public XConfiguration(){}
    public XConfiguration(String[] controllerPackage, boolean errorMessageDisplay, String filedAlias) {
        this.controllerPackage = controllerPackage;
        this.errorMessageDisplay = errorMessageDisplay;
        this.filedAlias = filedAlias;
    }

    public boolean isErrorMessageDisplay() {
        return errorMessageDisplay;
    }

    public void setErrorMessageDisplay(boolean errorMessageDisplay) {
        this.errorMessageDisplay = errorMessageDisplay;
    }

    public String[] getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String... controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getFiledAlias() {
        return filedAlias;
    }

    public void setFiledAlias(String filedAlias) {
        this.filedAlias = filedAlias;
    }

    public static XConfigurationBuilder builder() {
        return new XConfigurationBuilder();
    }

    public static class XConfigurationBuilder {
        private String[] controllerPackage;
        private boolean errorMessageDisplay = true;
        private String filedAlias;

        public XConfigurationBuilder controllerPackage(String... controllerPackage) {
            this.controllerPackage = controllerPackage;
            return this;
        }

        public XConfigurationBuilder errorMessageDisplay(boolean errorMessageDisplay) {
            this.errorMessageDisplay = errorMessageDisplay;
            return this;
        }

        public XConfigurationBuilder filedAlias(String filedAlias) {
            this.filedAlias = filedAlias;
            return this;
        }
        public XConfiguration build() {
            return new XConfiguration(controllerPackage, errorMessageDisplay, filedAlias);
        }
    }
}
