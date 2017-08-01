package com.c.j.w.xcheck.exception;

public class XCheckException extends RuntimeException {

    public XCheckException(String s) {
        this(s, null);
    }
    
    public XCheckException(String s, Throwable t) {
        super(s, t);
    }
}
