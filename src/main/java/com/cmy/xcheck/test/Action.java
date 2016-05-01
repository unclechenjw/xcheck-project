package com.cmy.xcheck.test;

import com.cmy.xcheck.support.annotation.Check;

public class Action {

    @Check(//value={"a@d", "b@$", "[a,b]@d:测试报错", "a@l(10)@ml(10)@l(3,5)"},
            value = {"a>b"},
            fieldAlias = "a=参数a,b=参数b")
    public void test0() {
        
    }

    @Check(value={"[a,b,c]@d"},
            fieldAlias = "a=参数a,b=参数b,c=参数c")
    public void test1() {

    }

    @Check(value={"if(a>b,a>c,b>c)"})
    public void test2() {

    }


}
