package com.c.j.w.xcheck.config.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.c.j.w.xcheck.support.XBean;
import com.c.j.w.xcheck.support.annotation.Check;
import com.c.j.w.xcheck.support.XBean;
import com.c.j.w.xcheck.support.annotation.Check;

public class XAnnotationConfigApplicationContext {

    private static final Map<Check, XBean> REPOSITORY = new ConcurrentHashMap<>();

    public static void register(Check xCheck, XBean xBean) {
        REPOSITORY.put(xCheck, xBean);
    }

    public static XBean getXBean(Check xCheck) {
        return REPOSITORY.get(xCheck);
    }

}
