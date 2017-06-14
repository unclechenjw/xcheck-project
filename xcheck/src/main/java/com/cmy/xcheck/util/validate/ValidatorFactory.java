package com.cmy.xcheck.util.validate;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 2016/12/11.
 */
@Component
public class ValidatorFactory implements ApplicationContextAware {

    private Map<String, ValidateMethod> ValidateMethodMap;
    private ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @PostConstruct
    public void init() {
        ValidateMethodMap = context.getBeansOfType(ValidateMethod.class);
    }

    public ValidateMethod getValidatorByAbbr(String methodAbbr) {
        return ValidateMethodMap.get(methodAbbr);
    }
}
