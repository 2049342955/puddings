package com.demo.core.validation.validator.impl;

import java.util.ArrayList;
import java.util.List;

import com.demo.core.validation.validator.AbstractValidator;
import com.demo.core.validation.validator.ILookupValuesProvider;
import com.demo.core.validation.validator.ValidateError;
import com.demo.core.validation.validator.ValidateUtils;
import org.apache.commons.beanutils.BeanUtils;

public class LookupValidator extends AbstractValidator {
    private static final String ERROR_MSG = "msg.error.validate.data.illegal";
    private String field;
    private String lookupType;

    public LookupValidator() {
    }

    public List<ValidateError> validate(Object bean) {
        ArrayList result = new ArrayList();

        try {
            String label = this.getLabel(bean, this.field);
            String valueStr = BeanUtils.getProperty(bean, this.field);
            ILookupValuesProvider lookupValuesProvider = ValidateUtils.getLookupValuesProvider();
            if (lookupValuesProvider != null && valueStr != null) {
                List<String> values = lookupValuesProvider.getLookupValues(this.lookupType);
                if (!values.contains(valueStr)) {
                    result.add(ValidateError.generateError(bean, this.field, "msg.error.validate.data.illegal", new Object[]{label}));
                }
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            result.add(ValidateError.generateError(bean, this.field, var7.getMessage()));
        }

        return result;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getLookupType() {
        return this.lookupType;
    }

    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }
}
