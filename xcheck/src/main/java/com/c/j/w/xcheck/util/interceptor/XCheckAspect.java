package com.c.j.w.xcheck.util.interceptor;

import com.c.j.w.xcheck.support.XCheckHandlerAdapter;
import com.c.j.w.xcheck.support.XResult;
import com.c.j.w.xcheck.util.CheckDispatcher;
import com.c.j.w.xcheck.util.WebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenjw
 */
@Aspect
@Component
public class XCheckAspect {

    @Autowired
    private CheckDispatcher checkDispatcher;
    @Autowired
    private XCheckHandlerAdapter xCheckHandlerAdapter;

    @Pointcut("@annotation(com.c.j.w.xcheck.support.annotation.Check)")
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

}
