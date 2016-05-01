package com.cmy.xcheck.support.annotation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cmy.xcheck.support.XBean;

public class XAnnotationConfigApplicationContext {
//    public static final Map<String, XBean> x = new ConcurrentHashMap<String, XBean>();
    
//    public void scan() {
//
//    }
//
//    public void refresh() {
//
//    }
//
//    public static void register(String identify, XBean xBean) {
//        x.put(identify, xBean);
//    }

    private static final Map<Check, XBean> REPOSITORY = new ConcurrentHashMap<Check, XBean>();
    public static void register(Check xCheck, XBean xBean) {
        REPOSITORY.put(xCheck, xBean);
    }

    public static XBean getXBean(Check xCheck) {
        return REPOSITORY.get(xCheck);
    }

}
