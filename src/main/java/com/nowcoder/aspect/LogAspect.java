package com.nowcoder.aspect;

//import org.apache.log4j.Logger;
//import org.apache.log4j.spi.LoggerFactory;

//import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

/**
 * Created by WCZ on 2016/6/28.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger("LogAspect.class");

    @Before("execution(* com.nowcoder.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for(Object args:joinPoint.getArgs()) {
            sb.append("args:"+args.toString()+"::");
        }
        logger.info("before Method"+sb.toString());
    }

    @After("execution(* com.nowcoder.controller.IndexController.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        logger.info("after Method");
    }

}
