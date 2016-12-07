package com.c.j.w.security.controller;

import com.c.j.w.security.redis.dao.JedisKey;
import com.c.j.w.security.redis.dao.JedisManager;
import com.c.j.w.security.util.ImageGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * 验证码获取接口
 * 杭州动享互联网技术有限公司
 * @Author chenjw
 * @Date 2016年12月07日
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    @Autowired
    private JedisManager jm;

    @GetMapping("sc")
    public String getSecurityCodeImg() {
        int i = new Random().nextInt(1000);
        jm.set(JedisKey.Security_Code + "" + i, 60, ""); // 验证码有效时间60秒
        return ImageGenerateUtil.geneBase64Img(i+"");
    }
}
