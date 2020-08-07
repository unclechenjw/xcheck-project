package com.github.xcheck.core;

import java.util.HashMap;
import java.util.Map;

import com.github.xcheck.support.annotation.Check;

public class AnnotationConfigApplicationContext {

    private static final Map<Check, XBean> REPOSITORY = new HashMap<>();

    public static void register(Check xCheck, XBean xBean) {
        REPOSITORY.put(xCheck, xBean);
    }

    public static XBean getXBean(Check xCheck) {
        return REPOSITORY.get(xCheck);
    }

}
