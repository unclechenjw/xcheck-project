package com.github.xcheck.core.validate;

import java.util.Map;

/**
 * @Author chenjw
 * @Date 2016年12月23日
 */
public class ValidateParam {
    private String mainFieldName;
    private String mainFieldVal;
    private String argumentsVal;
    private Map<String, String> fieldAliasMap;

    public ValidateParam(String mainFieldName, String mainFieldVal, String argumentsVal, Map<String, String> fieldAliasMap) {
        this.mainFieldName = mainFieldName;
        this.mainFieldVal = mainFieldVal;
        this.argumentsVal = argumentsVal;
        this.fieldAliasMap = fieldAliasMap;
    }

    public String getMainFieldName() {
        return mainFieldName;
    }

    public void setMainFieldName(String mainFieldName) {
        this.mainFieldName = mainFieldName;
    }

    public String getMainFieldVal() {
        return mainFieldVal;
    }

    public void setMainFieldVal(String mainFieldVal) {
        this.mainFieldVal = mainFieldVal;
    }

    public String getArgumentsVal() {
        return argumentsVal;
    }

    public void setArgumentsVal(String argumentsVal) {
        this.argumentsVal = argumentsVal;
    }

    public Map<String, String> getFieldAliasMap() {
        return fieldAliasMap;
    }

    public void setFieldAliasMap(Map<String, String> fieldAliasMap) {
        this.fieldAliasMap = fieldAliasMap;
    }
}
