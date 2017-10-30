package com.c.j.w.sample.controller;

import com.c.j.w.security.LimitMethod;
import com.c.j.w.security.annotation.AccessCondition;
import com.c.j.w.security.annotation.Security;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Author chenjw
 * @Date 2016年12月06日
 */
@RestController
public class SecurityDemoController {

    @GetMapping("s")
//    @Security()
//    @AccessCondition(limit = 2, seconds = 60)
    @AccessCondition(limit = 3, seconds = 60, module = "register", limitMethod = LimitMethod.Tel, fieldNameOfTel = "tel")
    @Security(limit = 3, seconds = 60, module = "register")
    public String s() {
        return "success";
    }
}
