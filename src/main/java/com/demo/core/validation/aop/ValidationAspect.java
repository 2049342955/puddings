package com.demo.core.validation.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.demo.core.validation.annotation.Validate;
import com.demo.core.validation.exception.ValidationException;
import com.demo.core.validation.validator.ValidateError;
import com.demo.core.validation.validator.ValidateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAspect {
    private static final Logger logger = LoggerFactory.getLogger(ValidationAspect.class);
    @Autowired
    private ValidateUtils validateUtils;

    public ValidationAspect() {
    }

    @Pointcut("@annotation(com.demo.core.validation.annotation.EnableValidation)")
    public void validate() {
    }

    @Around("validate()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Annotation[][] annotations = signature.getMethod().getParameterAnnotations();
        Object[] args = point.getArgs();
        List<ValidateError> errorList = new ArrayList();

        for(int i = 0; i < args.length; ++i) {
            Annotation[] ans = annotations[i];
            Annotation[] var8 = ans;
            int var9 = ans.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                Annotation an = var8[var10];
                if (an instanceof Validate) {
                    errorList.addAll(this.validateUtils.validate(args[i], ((Validate)an).value()));
                }
            }
        }

        if (!errorList.isEmpty()) {
            throw new ValidationException(errorList);
        } else {
            return point.proceed();
        }
    }
}
