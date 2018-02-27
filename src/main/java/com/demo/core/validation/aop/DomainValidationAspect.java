package com.demo.core.validation.aop;

import java.util.ArrayList;
import java.util.List;

import com.demo.core.validation.exception.ValidationException;
import com.demo.core.validation.validator.ValidateError;
import com.demo.core.validation.validator.ValidateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DomainValidationAspect {
    private static final Logger logger = LoggerFactory.getLogger(DomainValidationAspect.class);
    @Autowired
    private ValidateUtils validateUtils;

    public DomainValidationAspect() {
    }

    @Pointcut("execution(* *..mapper.*.insert(..))")
    public void validate() {
    }

    @Around("validate()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        List<ValidateError> errorList = new ArrayList();

        for(int i = 0; i < args.length; ++i) {
            Object arg = args[i];
            errorList.addAll(this.validateUtils.validateBean(arg));
        }

        if (!errorList.isEmpty()) {
            throw new ValidationException(errorList);
        } else {
            return point.proceed();
        }
    }
}
