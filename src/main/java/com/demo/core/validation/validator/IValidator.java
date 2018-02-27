package com.demo.core.validation.validator;

import java.util.List;

public interface IValidator {
    List<ValidateError> validate(Object var1);
}
