package com.cmy.xcheck.util.validate;

import com.cmy.xcheck.util.XHelper;

import java.util.Map;

/**
 * 校验方法基类
 * @Author chenjw
 * @Date 2016年12月26日
 */
public abstract class AbstractValidateMethod implements ValidateMethod {

    private XHelper xHelper;

    /**
     * 获取字段别名
     * @param field
     * @param fieldAliasMap
     * @return
     */
    protected String getAlias(String field, Map<String, String> fieldAliasMap) {
        return xHelper.getAlias(field, fieldAliasMap);
    }

    /**
     * 获取主字段别名
     * @param validateParam
     * @return
     */
    protected String getAlias(ValidateParam validateParam) {
        return xHelper.getAlias(validateParam.getMainFieldName(), validateParam.getFieldAliasMap());
    }
}