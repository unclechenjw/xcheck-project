package com.cmy.xcheck.support;

public class XResult {

    public static final int XCHECK_SESSION_EXPIRE = 100;
    public static final int XCHECK_SUCCESS = 200;
    public static final int XCHECK_FAILURE = 300;
    
    private int status = XCHECK_SUCCESS;
    private String message;

    public String getMessage() {
        return message;
    }
    public XResult failure(String errorMsg) {
        this.status = XCHECK_FAILURE;
        this.message = errorMsg;
        return this;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public boolean isPass() {
        return status == XCHECK_SUCCESS;
    }
    public boolean isNotPass() {
        return !isPass();
    }
    
}
