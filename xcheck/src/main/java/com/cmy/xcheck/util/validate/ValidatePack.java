package com.cmy.xcheck.util.validate;

/**
 * Created by kevin on 2016/12/11.
 */
public class ValidatePack {
    private ValidateMethod validateMethod;
    private String argument;

    public ValidatePack(ValidateMethod validateMethod, String argument) {
        this.validateMethod = validateMethod;
        this.argument = argument;
    }

    public ValidateMethod getValidateMethod() {
        return validateMethod;
    }

    public void setValidateMethod(ValidateMethod validateMethod) {
        this.validateMethod = validateMethod;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
