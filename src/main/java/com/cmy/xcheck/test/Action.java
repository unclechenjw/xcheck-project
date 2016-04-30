package com.cmy.xcheck.test;

import com.cmy.xcheck.support.annotation.Check;

public class Action {

    @Check(value={"a@d", "b@$"})
    public void get() {
        
    }

    @Check(value={"[a,b,c]@d"})
    public void test0() {

    }

    @Check(value={"if(a>b,a>c,b>c)"})
    public void test1() {

    }


}
