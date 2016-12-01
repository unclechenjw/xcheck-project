package com.cmy.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by kevin on 2016/12/1.
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @GetMapping
    public String test() {
        return "success" + new Random().nextInt(100);
    }

}
