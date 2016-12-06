package com.cmy.sample.controller;

import com.c.j.w.security.annotation.Security;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 杭州动享互联网技术有限公司
 *
 * @Author chenjw
 * @Date 2016年12月06日
 */
@RestController
public class SecurityController {

    @Security()
    @GetMapping("s")
    public String s() {
        return "success";
    }
}
