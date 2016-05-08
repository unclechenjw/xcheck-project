package com.cmy.xcheck.exception;

public class ExpressionDefineException extends XCheckException {

    private static final long serialVersionUID = -5834715077486484703L;

    public ExpressionDefineException(String s) {
        this(s, null);
    }
    
    public ExpressionDefineException(String s, Throwable t) {
        super("表达式定义有误:" + s, t);
    }
}
