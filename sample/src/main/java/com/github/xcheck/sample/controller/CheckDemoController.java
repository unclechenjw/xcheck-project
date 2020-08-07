package com.github.xcheck.sample.controller;

import com.github.xcheck.support.annotation.Check;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by kevin on 2016/12/1.
 */
@RestController
@RequestMapping("xcheck")
public class CheckDemoController {


    @Check({
            "a",                     // 没有任何校验方法声明时，只是简单验证参数不能为空
            "a@d",                   // 参数a不能为空必须为数字
            "b@w",                   // 参数b不能为空必须为字母
            "c#e",                   // @表示不能为空必须满足校验条件 #可空或满足条件，参数c可空或者必须为邮箱格式
            "d@l(3)",                // 参数d长度必须3位
            "e@l(1,3)",              // 参数e长度必须为1到3位
            "f@ml(10)",              // 参数f的最大长度必须为10位
            "g@id",                  // g必须为身份证号码格式，空参数默认为中国居民身份证
            "h@in(1,2,3)",           // h必须为1、2或3
            "a.x#p",                 // a对象的x属性可空或者必须为手机号码
            "[a,b,c]@ml(12)",        // 如果多个参数校验规则相同时候，可用中括号加字段逗号分隔，@校验方法，a,b,c字段最大长度为12为
            "startTime<endTime",     // 开始时间必须小于结束时间
    })
    @GetMapping("t1")
    public String t1() {
        return "Passed validation";
    }


    /**
     * 为避免字段直接暴露客户端，可通过三种方法对参数字段设置别名
     * 1、如果字段在项目中非常常见，在application.yml中配置xcheck:.column-alias: pageSize=页数, pageNum=页数
     *    格式为：字段命名=别名, 多个使用逗号分隔，中间可以加空格
     * 2、在Check注解的fieldAlias属性配置备注，业务接口参数|出现频率不高的可使用此方式配置，直观
     * 3、在校验公式后配置错误提示内容，例如以下a字段的校验，好处是可根据产品特性给用户响应有趣的错误提示
     */
    @Check(value = {
            "a@d:大兄弟！a参数输入不正确啊",                       // 在校验规则后加冒号：提示错误信息，校验不通过时优先选择定制错误信息
            "startTime<endTime"},                              // 开始时间必须小于结束时间
            fieldAlias = "startTime=开始时间,endTime=结束时间")  // 当startTime<endTime校验不通过时正常会提示 endTime必须大于startTime
                                                               // 配置fieldAlias别名后就会提示： 结束时间必须大于开始时间
                                                               // 这样更安全，对用户更友好
    @GetMapping("t2")
    public String t2() {
        return "Passed validation";
    }

    /**
     * Url PathVariable参数的校验
     * 跟表单提交参数校验方法一样
     * @param p1
     * @param p2
     */
    @Check({"p1@d",
            "p2@ml(10)"})
    @GetMapping("t3/{p1}/{p2}")
    public String t3(@PathVariable String p1, @PathVariable String p2) {
        System.out.println(p1);
        System.out.println(p2);
        return "Passed validation";
    }

    /**
     * 条件表达式校验
     * @return
     */
    @Check({
            "if('a=1', 'b=2', 'c=3')",              // 如果a等于1时b必须等于为2，否则c等于3
            "if('i=1', 'j=2')"                      // i等于1时j必须为2，i不等于1时校验通过
                                                    // 使用if规则校验必须添加错误提示信息，否则错误会让用户觉得无厘头
    })
    @GetMapping("t4")
    public String t4() {
        return "Passed validation";
    }

    @Check({
            "a@d",                   // 参数a不能为空必须为数字
            "b@w",                   // 参数b不能为空必须为字母
    })
    @PostMapping("x")
    public String x(@RequestBody HashMap hashMap) {
        System.out.println(hashMap);
        return "Passed validation";
    }
}
