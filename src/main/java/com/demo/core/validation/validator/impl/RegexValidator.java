package com.demo.core.validation.validator.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.demo.core.validation.validator.AbstractValidator;
import com.demo.core.validation.validator.ValidateError;
import org.apache.commons.beanutils.BeanUtils;

public class RegexValidator extends AbstractValidator {
    private static final String ERROR_MSG = "msg.error.validate.regex.notmatch";
    private String pattern;
    private List<String> fields;
    private boolean nullable;

    public RegexValidator() {
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<String> getFields() {
        return this.fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<ValidateError> validate(Object bean) {
        List<ValidateError> result = new ArrayList();
        Pattern pattern = Pattern.compile(this.pattern);
        Iterator var4 = this.fields.iterator();

        while(var4.hasNext()) {
            String field = (String)var4.next();

            try {
                String label = this.getLabel(bean, field);
                Object value = BeanUtils.getProperty(bean, field);
                if (!this.isNullable() && value == null) {
                    result.add(ValidateError.generateError(bean.getClass(), field, "msg.error.validate.regex.notmatch", new Object[]{label}));
                } else if (value != null && !pattern.matcher(value.toString()).find()) {
                    result.add(ValidateError.generateError(bean.getClass(), field, "msg.error.validate.regex.notmatch", new Object[]{label}));
                }
            } catch (Exception var8) {
                var8.printStackTrace();
                result.add(ValidateError.generateError(bean.getClass(), field, var8.getMessage()));
            }
        }

        return result;
    }
}
