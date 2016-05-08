package com.cmy.xcheck.exception;

public class XCheckException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -5282573408499150874L;

    public XCheckException(String s) {
        this(s, null);
    }
    
    public XCheckException(String s, Throwable t) {
        super(s, t);
    }
}
