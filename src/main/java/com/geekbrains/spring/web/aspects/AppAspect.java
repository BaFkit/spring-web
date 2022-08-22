package com.geekbrains.spring.web.aspects;

import com.geekbrains.spring.web.services.StatisticService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Data
@Log
@Aspect
@Component
@RequiredArgsConstructor
public class AppAspect {

    private final StatisticService statisticService;


    @Around("execution(public * com.geekbrains.spring.web.services.ProductsService.*(..))")
    public Object methodProfilingProductsService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        statisticService.setDurationProductsService(statisticService.getDurationProductsService() + duration);
        return out;
    }

    @Around("execution(public * com.geekbrains.spring.web.services.UserService.*(..))")
    public Object methodProfilingUserService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        statisticService.setDurationUserService(statisticService.getDurationUserService() + duration);
        return out;
    }

    @Around("execution(public * com.geekbrains.spring.web.services.OrderService.*(..))")
    public Object methodProfilingOrderService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        statisticService.setDurationOrderService(statisticService.getDurationOrderService() + duration);
        return out;
    }

    @AfterReturning(pointcut = "execution(public * com.geekbrains.spring.web.services.UserService.loadUserByUsername(..))",
            returning = "result")
    public void afterGetUserName(JoinPoint joinPoint, UserDetails result) {
        log.info("Залогинился пользователь - " + result.getUsername());
    }

}
