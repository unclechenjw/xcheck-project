package com.cmy.xcheck.support.annotation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cmy.xcheck.support.XBean;

public class XAnnotationConfigApplicationContext {

    private static final Map<Check, XBean> REPOSITORY = new ConcurrentHashMap<>();

    public static void register(Check xCheck, XBean xBean) {
        REPOSITORY.put(xCheck, xBean);
    }

    public static XBean getXBean(Check xCheck) {
        return REPOSITORY.get(xCheck);
    }

}
