package com.c.j.w.xcheck.exception;

public class UnsupportedExpressionException extends XCheckException {

    public UnsupportedExpressionException(String s) {
        this(s, null);
    }
    
    public UnsupportedExpressionException(String s, Throwable t) {
        super(s, t);
    }
}
