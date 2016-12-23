package com.cmy.xcheck.util.validate;

/**
 * Created by kevin on 2016/12/11.
 */
public class ValidatePack {
    private ValidateMethod validateMethod;
    private String arguments;

    public ValidatePack(ValidateMethod validateMethod, String arguments) {
        this.validateMethod = validateMethod;
        this.arguments = arguments;
    }

    public ValidateMethod getValidateMethod() {
        return validateMethod;
    }

    public void setValidateMethod(ValidateMethod validateMethod) {
        this.validateMethod = validateMethod;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
}
