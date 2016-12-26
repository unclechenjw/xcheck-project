package com.cmy.sample.controller;

import com.cmy.xcheck.support.annotation.Check;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kevin on 2016/12/1.
 */
@RestController
@RequestMapping("demo")
public class CheckDemoController {

    @Check({
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
    })
    @GetMapping
    public String test(@ModelAttribute Foo foo) {
        return "success" + new Random().nextInt(100);
    }

}
