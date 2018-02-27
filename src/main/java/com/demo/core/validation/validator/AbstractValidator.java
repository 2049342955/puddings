package com.demo.core.validation.validator;

import java.util.List;

public abstract class AbstractValidator implements IValidator {
    public AbstractValidator() {
    }

    public abstract List<ValidateError> validate(Object var1);

    protected String getLabel(Object bean, String field) {
        String label = ("label." + bean.getClass().getName() + "." + field).toLowerCase();
        return label;
    }
}
