package com.cmy.xcheck.util;

public class CheckResult {

    /**
     *  100:session expire
     *  200:pass the validation 
     *  300:not pass the validation 
     */
    private int status = 200;
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }
    public CheckResult setErrorMsg(String errorMsg) {
        this.status = 300;
        this.errorMsg = errorMsg;
        return this;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public boolean isPass() {
        return status == 200;
    }
    public boolean isNotPass() {
        return !isPass();
    }
    
}
