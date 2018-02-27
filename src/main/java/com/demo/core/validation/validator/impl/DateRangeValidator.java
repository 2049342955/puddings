package com.demo.core.validation.validator.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo.core.validation.validator.AbstractValidator;
import com.demo.core.validation.validator.ValidateError;
import org.apache.commons.beanutils.PropertyUtils;

public class DateRangeValidator extends AbstractValidator {
    private static final String ERROR_MSG = "msg.error.validate.date.range";
    private String field;
    private String earlier;

    public DateRangeValidator() {
    }

    public List<ValidateError> validate(Object bean) {
        ArrayList result = new ArrayList();

        try {
            String label = this.getLabel(bean, this.field);
            String earlierLabel = this.getLabel(bean, this.earlier);
            Date date = (Date)PropertyUtils.getProperty(bean, this.field);
            if (date != null) {
                Date earlierDate = (Date)PropertyUtils.getProperty(bean, this.earlier);
                if (earlierDate.compareTo(date) > 0) {
                    result.add(ValidateError.generateError(bean.getClass(), this.field, "msg.error.validate.date.range", new Object[]{label, earlierLabel}));
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

    public String getEarlier() {
        return this.earlier;
    }

    public void setEarlier(String earlier) {
        this.earlier = earlier;
    }
}
