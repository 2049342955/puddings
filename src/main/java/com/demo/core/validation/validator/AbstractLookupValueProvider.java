package com.demo.core.validation.validator;

public abstract class AbstractLookupValueProvider implements ILookupValuesProvider {
    public AbstractLookupValueProvider() {
    }

    public void init() {
        ValidateUtils.setLookupValuesProvider(this);
    }
}
