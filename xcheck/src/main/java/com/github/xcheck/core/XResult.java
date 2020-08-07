package com.github.xcheck.core;

public class XResult {

    public static final int Success = 200;
    public static final int Failure = 400;
    
    private int status;
    private String message;

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

}
