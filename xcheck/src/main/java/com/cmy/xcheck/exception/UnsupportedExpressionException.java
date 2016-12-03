package com.cmy.xcheck.exception;

public class UnsupportedExpressionException extends XCheckException {

    private static final long serialVersionUID = -5834715077486484703L;

    public UnsupportedExpressionException(String s) {
        this(s, null);
    }
    
    public UnsupportedExpressionException(String s, Throwable t) {
        super("未支持的表达式:" + s, t);
    }
}
