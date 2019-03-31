package com.cybercom.fruitstore.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * This is an Aspect for logging cross cuttingly in our Service beans
 */
@Aspect
@Configuration
public class AfterBusinessLogicAspect {
    private final Logger logger = LoggerFactory.getLogger(AfterBusinessLogicAspect.class);

    /**
     * This method works on each methods of annotated classes with @Service, it is useful for tracing business logic
     *
     * @param joinPoint is the method
     */
    @After(value = "@within(org.springframework.stereotype.Service)")
    public void after(JoinPoint joinPoint) {
        logger.info("This has just happened in service layer : {}", joinPoint);
    }
}
