package com.demo.core.validation.validator.impl;

import java.util.ArrayList;
import java.util.List;

import com.demo.core.validation.validator.AbstractValidator;
import com.demo.core.validation.validator.ValidateError;
import org.apache.commons.beanutils.BeanUtils;

public class LengthValidator extends AbstractValidator {
    private static final String ERROR_MSG = "msg.error.validate.length.gratter.than";
    private String field;
    private int maxLength;

    public LengthValidator() {
    }

    public List<ValidateError> validate(Object bean) {
        ArrayList result = new ArrayList();

        try {
            String label = this.getLabel(bean, this.field);
            String valueStr = BeanUtils.getProperty(bean, this.field);
            if (valueStr != null && valueStr.length() > this.maxLength) {
                result.add(ValidateError.generateError(bean, this.field, "msg.error.validate.length.gratter.than", new Object[]{label, this.maxLength}));
            }
        } catch (Exception var5) {
            var5.printStackTrace();
            result.add(ValidateError.generateError(bean, this.field, var5.getMessage()));
        }

        return result;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
