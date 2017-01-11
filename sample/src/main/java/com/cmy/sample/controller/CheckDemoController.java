package com.cmy.sample.controller;

import com.cmy.xcheck.support.annotation.Check;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * Created by kevin on 2016/12/1.
 */
@RestController
@RequestMapping("demo")
public class CheckDemoController {

    @Check(value = {
            "a@d",
            "b@w",
            "c@e",
            "a.a#p",
            "[a,b,c]@ml(12)",
            "e@ml(f)",
            "id@id(type)",
            "name@ml(nameType)",
            "foo.list@d",
            "startTime<endTime",
            "orderType@in(1,2,3)",
    },
    fieldAlias = "orderType=订单类型,id=身份证号码")
    @GetMapping
    public String test1() {
        return "success" + new Random().nextInt(100);
    }

    @Check({"p1@d", "p2@ml(10)"})
    @GetMapping({"test/{p1}/{p2}",
        "t/{p1}/t/{p2}"})
    public String test2(@PathVariable String p1,
                        @PathVariable String p2) {
        System.out.println(p1);
        System.out.println(p2);
        return "success" + new Random().nextInt(100);
    }

    @Check({"[arg.a, arg.b]:参数不正确"})
    @GetMapping("test3")
    public String test3() {
        return "success" + new Random().nextInt(100);
    }

}
