package com.cmy.xcheck.util.validate;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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
        ValidateMethodMap = new HashMap<>();
        Map<String, ValidateMethod> beansOfType = context.getBeansOfType(ValidateMethod.class);
        System.out.println(beansOfType);
        for (ValidateMethod vm : beansOfType.values()) {
            ValidateMethodMap.put(vm.getMethodAttr(), vm);
        }
    }

    public ValidateMethod getValidatorByAbbr(String methodAbbr) {
        return ValidateMethodMap.get(methodAbbr);
    }
}
