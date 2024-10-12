package com.kolos.bookstore.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.kolos.bookstore.service.impl.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.debug("Executing method: {}", joinPoint.getSignature().getName());
        log.debug("With arguments: {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.kolos.bookstore.service.impl.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method {} executed successfully", joinPoint.getSignature().getName());
        if (result != null) {
            log.debug("Returned value: {}", result);
        }
    }

    @AfterThrowing(pointcut = "execution(* com.kolos.bookstore.service.impl.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("Method {} threw an exception: {}", joinPoint.getSignature().getName(), error.getMessage());

        log.error("Exception: ", error);
    }
}
