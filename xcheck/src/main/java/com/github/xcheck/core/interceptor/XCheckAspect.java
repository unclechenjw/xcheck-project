package com.github.xcheck.core.interceptor;

import com.github.xcheck.exception.XCheckException;
import com.github.xcheck.core.XCheckHandlerAdapter;
import com.github.xcheck.core.XResult;
import com.github.xcheck.core.CheckDispatcher;
import com.github.xcheck.core.util.WebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author chenjw
 * @date 2020/8/7
 **/
@Order(1)
@Aspect
@Component
public class XCheckAspect {

    @Autowired
    private CheckDispatcher checkDispatcher;
    @Autowired
    private XCheckHandlerAdapter xCheckHandlerAdapter;

    @Pointcut("@annotation(com.github.xcheck.support.annotation.Check)")
    public void handleMethod() {

    }


    @Around("handleMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        XResult checkResult = checkDispatcher.check(joinPoint);
        if (checkResult.isNotPass()) {
            // 返回校验不通过原因,错误信息， status：响应状态, message：校验不通过原因
            xCheckHandlerAdapter.checkFailHandle(
                    WebUtil.getCurrentRequest(),
                    WebUtil.getCurrentResponse(),
                    checkResult.getMessage());
            return null;
        }
        return joinPoint.proceed();
    }

    @PostConstruct
    public void checkHandlerAdapterInstanceExists() {
        if (xCheckHandlerAdapter == null) {
            throw new XCheckException("XCheckHandlerAdapter is unimplemented exception");
        }
    }
}
