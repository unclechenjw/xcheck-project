package com.cmy.sample.controller;

import java.util.ArrayList;

/**
 * Created by kevin on 2016/12/17.
 */
public class Foo {
    private ArrayList<String> a;
    private ArrayList<String> b;

    public ArrayList<String> getA() {
        return a;
    }

    public void setA(ArrayList<String> a) {
        this.a = a;
    }

    public ArrayList<String> getB() {
        return b;
    }

    public void setB(ArrayList<String> b) {
        this.b = b;
    }

    public static void main(String[] args) {
        String s = "a[0].s[1]d".replaceAll("\\[\\d+\\]", "");
//        String s = "a[0].s[1]d".replaceAll("/d+/]]", "");
        System.out.println(s);
    }
}