package com.cmy.xcheck.support;

import com.cmy.xcheck.config.XMessageBuilder;
import com.cmy.xcheck.support.annotation.ClassPathXBeanDefinitionScanner;

import javax.annotation.PostConstruct;

/**
 * 杭州动享互联网技术有限公司
 * @Author chenjw
 * @Date 2016年05月09日
 */
public class XCheckContext {

    /** 扫描包目录 */
    private String[] scanPackage;
    /** 错误信息是否提示 */
    private boolean errorMessageDisplay = true;
    /** 语言环境 */
    private String locale;

    @PostConstruct
    private void init() {
        // 扫包 解析校验对象
        new ClassPathXBeanDefinitionScanner(scanPackage).scanAndParse();
        // 错误提示
        XMessageBuilder.ErrorMessageDisplay = errorMessageDisplay;
        XMessageBuilder.locale = locale;
        XMessageBuilder.init();
    }

    public void setScanPackage(String[] scanPackage) {
        this.scanPackage = scanPackage;
    }

    public void setErrorMessageDisplay(boolean errorMessageDisplay) {
        this.errorMessageDisplay = errorMessageDisplay;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
