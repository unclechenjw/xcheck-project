package com.c.j.w.sample.controller;

import com.c.j.w.xcheck.support.annotation.Check;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kevin on 2016/12/1.
 */
@RestController
@RequestMapping("demo")
public class CheckDemoController {


    @Check({
            "a@d",                   // 参数a不能为空必须为数字
            "b@w",                   // 参数b不能为空必须为字母
            "c#e",                   // 参数c可空或者必须为邮箱格式    @表示不能为空必须满足校验条件  #可空或满足条件
            "d@l(3)",                // 参数d长度必须3位
            "e@l(1,3)",              // 参数e长度必须为1到3位
            "[a,b,c]@ml(12)",        // a,b,c字段最大长度为12为
            "f@ml(3)",               // f字段的最大长度必须为3位
            "g@id(type)",            // g必须为身份证号码格式，空参数默认为中国居民身份证
            "h@in(1,2,3)",           // h必须为1、2或3
            "a.x#p",                 // a对象的x属性可空或者必须为手机号码
            "startTime<endTime",     // 开始时间必须小于结束时间
    })
    @GetMapping("t1")
    public String t1() {
        return "validation passed";
    }


    /**
     * 为避免字段直接暴露客户端，可通过三种方法对参数字段设置别名
     * 1、在XConfig配置XCheckProperties类的fieldAlias全局字段别名属性，建议常用字段在此配置
     * 2、在Check注解的fieldAlias属性配置备注，业务接口参数|出现频率不高的可使用此方式配置，直观
     * 3、在校验公式后配置错误提示内容，例如以下a字段的校验，好处是可根据产品特性给用户响应有趣的错误提示
     */
    @Check(value = {
            "a@d:大兄弟！字段a输入不正确啊",
            "startTime<endTime"},    // 开始时间必须小于结束时间
            fieldAlias = "startTime=开始时间,endTime=结束时间")
    @GetMapping("t2")
    public String t2() {
        return "validation passed";
    }

    /**
     * Url PathVariable参数的校验
     * 跟表单提交参数校验方法一样
     * @param p1
     * @param p2
     */
    @Check({"p1@d", "p2@ml(10)"})
    @GetMapping("t3/{p1}/{p2}")
    public String t3(@PathVariable String p1, @PathVariable String p2) {
        System.out.println(p1);
        System.out.println(p2);
        return "validation passed";
    }

    /**
     * 条件表达式校验
     * @return
     */
    @Check({
            "if('a=1', 'b=2', 'c=3')", // 如果a等于1时b必须等于为2，否则c等于3
            "if('i=1', 'j=2')"        // i等于1时j必须为2，i不等于1时校验通过
    })
    @GetMapping("t4")
    public String t4() {
        return "validation passed";
    }

}
