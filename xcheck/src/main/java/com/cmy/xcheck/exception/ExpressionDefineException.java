package com.cmy.xcheck.exception;

public class ExpressionDefineException extends XCheckException {

    public ExpressionDefineException(String s) {
        this(s, null);
    }
    
    public ExpressionDefineException(String s, Throwable t) {
        super("表达式定义有误:" + s, t);
    }
}
