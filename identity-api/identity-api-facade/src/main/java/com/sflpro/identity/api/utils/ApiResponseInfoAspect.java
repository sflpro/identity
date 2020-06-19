package com.sflpro.identity.api.utils;

import com.sflpro.identity.api.common.dtos.AbstractApiResponse;
import com.sflpro.identity.api.utils.impl.ApiResponseInfoServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
@Aspect
@Component
public class ApiResponseInfoAspect {

    private static final Logger logger = LoggerFactory.getLogger(ApiResponseInfoAspect.class);

    private ApiResponseInfoService responseTimeService;

    public ApiResponseInfoAspect() {
        this.responseTimeService = new ApiResponseInfoServiceImpl();
    }

    // the execution of any method defined in the endpoints package or a sub-package:
    @Pointcut("execution(* com.sflpro.identity.api.endpoints..*.*(..))")
    public void anyEndpointMethods() {
    }

    // the execution of any method returning AbstractApiResponse object:
    @Pointcut("execution(public com.sflpro.identity.api.common.dtos.AbstractApiResponse+ *(..))")
    public void anyMethodReturningResponse() {
    }

    // any join point (method execution only in Spring AOP) where the executing method has an javax.ws.rs.GET annotation:
    @Pointcut("@annotation(javax.ws.rs.GET)")
    public void anyMethodWithWSRSGetAnnotation() {
    }

    // any join point (method execution only in Spring AOP) where the executing method has an javax.ws.rs.POST annotation:
    @Pointcut("@annotation(javax.ws.rs.POST)")
    public void anyMethodWithWSRSPostAnnotation() {
    }

    // any join point (method execution only in Spring AOP) where the executing method has an javax.ws.rs.PUT annotation:
    @Pointcut("@annotation(javax.ws.rs.PUT)")
    public void anyMethodWithWSRSPutAnnotation() {
    }

    // any join point (method execution only in Spring AOP) where the executing method has an javax.ws.rs.DELETE annotation:
    @Pointcut("@annotation(javax.ws.rs.DELETE)")
    public void anyMethodWithWSRSDeleteAnnotation() {
    }

    // any join point (method execution only in Spring AOP) where the executing method has an javax.ws.rs.HEAD annotation:
    @Pointcut("@annotation(javax.ws.rs.HEAD)")
    public void anyMethodWithWSRSHeadAnnotation() {
    }

    @Around("anyEndpointMethods() && anyMethodReturningResponse() " +
            "&& (anyMethodWithWSRSGetAnnotation()" +
            "|| anyMethodWithWSRSPostAnnotation()" +
            "|| anyMethodWithWSRSPutAnnotation()" +
            "|| anyMethodWithWSRSDeleteAnnotation()" +
            "|| anyMethodWithWSRSHeadAnnotation())")
    public Object elapseTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();

        Object result = joinPoint.proceed();
        if (result instanceof AbstractApiResponse) {
            responseTimeService.propagateApiResponse((AbstractApiResponse) result, startTime);
        } else {
            logger.warn("ApiResponseTimeAspect called for " + joinPoint.getSignature().getDeclaringTypeName() + "." +
                    joinPoint.getSignature().getName());
        }
        return result;
    }
}
