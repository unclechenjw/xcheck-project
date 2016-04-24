package com.cmy.xcheck.test;

import com.cmy.xcheck.support.annotation.Check;

public class Action {

    @Check(value={"a@d", "b@$"})
    public void get() {
        
    }
}
