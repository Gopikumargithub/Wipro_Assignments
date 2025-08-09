package com.wipro.gk.quizappmonorepo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.wipro.gk.quizappmonorepo.service.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("Method called: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.wipro.gk.quizappmonorepo.service.*.*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        System.out.println("Method finished: " + joinPoint.getSignature().toShortString() + " | Returned: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.wipro.gk.quizappmonorepo.service.*.*(..))", throwing = "ex")
    public void logAfterException(JoinPoint joinPoint, Throwable ex) {
        System.out.println("Exception in: " + joinPoint.getSignature().toShortString() + " | Exception: " + ex.getMessage());
    }
}
