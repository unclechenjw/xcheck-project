package com.c.j.w.xcheck.config.context;

import java.util.HashMap;
import java.util.Map;

import com.c.j.w.xcheck.support.XBean;
import com.c.j.w.xcheck.support.annotation.Check;

public class XAnnotationConfigApplicationContext {

    private static final Map<Check, XBean> REPOSITORY = new HashMap<>();

    public static void register(Check xCheck, XBean xBean) {
        REPOSITORY.put(xCheck, xBean);
    }

    public static XBean getXBean(Check xCheck) {
        return REPOSITORY.get(xCheck);
    }

}
