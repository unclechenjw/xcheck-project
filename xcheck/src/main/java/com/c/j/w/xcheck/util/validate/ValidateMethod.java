package com.c.j.w.xcheck.util.validate;

import com.c.j.w.xcheck.support.XResult;
import com.c.j.w.xcheck.support.XResult;
import org.springframework.stereotype.Component;

/**
 * 校验方法接口
 * @Author chenjw
 * @Date 2016年12月08日
 */
@Component
public interface ValidateMethod {

    XResult validate(ValidateParam validateParam);

}
