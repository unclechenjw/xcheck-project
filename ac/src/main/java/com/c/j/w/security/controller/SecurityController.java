package com.c.j.w.security.controller;

import com.c.j.w.security.redis.dao.JedisKey;
import com.c.j.w.security.redis.dao.JedisManager;
import com.c.j.w.security.util.VerifyCodeUtil;
import com.c.j.w.security.util.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码获取接口
 * @Author chenjw
 * @Date 2016年12月07日
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    @Autowired
    private JedisManager jm;

    @GetMapping("sc")
    public Map<String, String> getSecurityCodeImg() {
        VerifyCode verifyCode = VerifyCodeUtil.generateVerifyCodeData();
        Map<String, String> map = new HashMap<>();
        map.put("status", "200");
        map.put("infobean", verifyCode.getBase64Str());
        jm.set(JedisKey.Security_Code + verifyCode.getCode().toLowerCase(), 60, ""); // 验证码有效时间60秒
        return map;
    }
}
