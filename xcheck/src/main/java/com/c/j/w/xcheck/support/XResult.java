package com.c.j.w.xcheck.support;

public class XResult {

    public static final int XCHECK_SESSION_EXPIRE = 1100;
    public static final int Success = 200;
    public static final int Failure = 300;
    
    private int status = Success;
    private String message;

//    public XResult() {
//    }
//    public XResult(int status) {
//        this.status = status;
//    }
    public XResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public static XResult failure(String msg) {
        return new XResult(Failure, msg);
    }
    public static XResult success() {
        return new XResult(Success, null);
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public boolean isPass() {
        return status == Success;
    }
    public boolean isNotPass() {
        return !isPass();
    }
    public boolean isSessionExpire() {
        return status == XCHECK_SESSION_EXPIRE;
    }

}
