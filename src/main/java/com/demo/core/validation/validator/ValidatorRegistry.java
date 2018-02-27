package com.demo.core.validation.validator;

import com.demo.core.validation.validator.impl.*;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ValidatorRegistry {
    private static Map<String, Class<? extends IValidator>> validatorMap = new ConcurrentHashMap();

    public ValidatorRegistry() {
    }

    public static void registryValidator(String name, Class<? extends IValidator> validator) {
        validatorMap.put(name, validator);
    }

    public static Class<? extends IValidator> getValidatorClass(String name) {
        return (Class)validatorMap.get(name);
    }

    public static Set<String> getValidatorNames() {
        return validatorMap.keySet();
    }

    static {
        registryValidator("notNull", NotNullValidator.class);
        registryValidator("empty", NotEmptyValidator.class);
        registryValidator("regex", RegexValidator.class);
        registryValidator("length", LengthValidator.class);
        registryValidator("dateRange", DateRangeValidator.class);
        registryValidator("lookup", LookupValidator.class);
    }
}
