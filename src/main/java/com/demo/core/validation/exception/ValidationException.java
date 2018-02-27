package com.demo.core.validation.exception;

import com.demo.core.validation.validator.ValidateError;

import java.util.List;

public class ValidationException extends RuntimeException {
    public static final String ERROR_CODE = "1001";
    private List<ValidateError> errors;

    public ValidationException(List<ValidateError> errors) {
        this.errors = errors;
    }

    public List<ValidateError> getErrors() {
        return this.errors;
    }

    public void setErrors(List<ValidateError> errors) {
        this.errors = errors;
    }
}
