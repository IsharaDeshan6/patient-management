package com.pm.patientservice.aspects;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PatientServiceMetrics {

    private final MeterRegistry meterRegistry;


    @Around("execution(* com.pm.patientservice.service.impl.PatientServiceImpl.getAllPatients(..))")
    public Object monitorGetPatients(ProceedingJoinPoint joinPoint) throws Throwable{
        meterRegistry.counter("custom.redis.cache.miss", "cache", "patients").increment();

        Object result = joinPoint.proceed();
        return result;
    }


}
