package com.cmy.xcheck.util.validate;

import com.cmy.xcheck.util.XHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 校验方法基类
 * @Author chenjw
 * @Date 2016年12月26日
 */
public abstract class AbstractValidateMethod implements ValidateMethod {

    @Autowired
    private XHelper xHelper;

    /**
     * 获取字段别名
     * @param field
     * @param fieldAliasMap
     * @return
     */
    protected String getFieldAlias(String field, Map<String, String> fieldAliasMap) {
        return xHelper.getAlias(field, fieldAliasMap);
    }

    /**
     * 获取主字段别名
     * @param validateParam
     * @return
     */
    protected String getFieldAlias(ValidateParam validateParam) {
        return xHelper.getAlias(validateParam.getMainFieldName(), validateParam.getFieldAliasMap());
    }
}
