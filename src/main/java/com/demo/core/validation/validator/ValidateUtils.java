package com.demo.core.validation.validator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.core.validation.annotation.DateRange;
import com.demo.core.validation.annotation.Lookup;
import com.demo.core.validation.annotation.MaxLength;
import com.demo.core.validation.annotation.NotEmpty;
import com.demo.core.validation.annotation.NotNull;
import com.demo.core.validation.annotation.Regex;
import com.demo.core.validation.validator.impl.DateRangeValidator;
import com.demo.core.validation.validator.impl.LengthValidator;
import com.demo.core.validation.validator.impl.LookupValidator;
import com.demo.core.validation.validator.impl.NotEmptyValidator;
import com.demo.core.validation.validator.impl.NotNullValidator;
import com.demo.core.validation.validator.impl.RegexValidator;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidateUtils {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtils.class);
    private static final String LEFT_PATTERN = "{";
    private static final String RIGHT_PATTERN = "}";
    private static ILookupValuesProvider lookupValuesProvider;

    public ValidateUtils() {
    }

    public static ILookupValuesProvider getLookupValuesProvider() {
        return lookupValuesProvider;
    }

    public static void setLookupValuesProvider(ILookupValuesProvider lookupValuesProvider) {
        lookupValuesProvider = lookupValuesProvider;
    }

    public List<ValidateError> validate(Object bean, String rules) {
        ArrayList validateErrors = new ArrayList();

        try {
            if (!rules.startsWith("{")) {
                rules = "{" + rules + "}";
            }

            List<IValidator> validators = this.jsonStrToValidator(rules);
            Iterator var5 = validators.iterator();

            while(var5.hasNext()) {
                IValidator validator = (IValidator)var5.next();
                validateErrors.addAll(validator.validate(bean));
            }
        } catch (IOException var7) {
            var7.printStackTrace();
            logger.error("error: ", var7);
        }

        return validateErrors;
    }

    public List<ValidateError> validateBean(Object bean) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<ValidateError> errorList = new ArrayList();
        if (bean instanceof Collection) {
            Collection collection = (Collection)bean;
            Iterator it = collection.iterator();

            while(it.hasNext()) {
                errorList.addAll(this.validateBean(it.next()));
            }
        } else {
            Field[] fields = FieldUtils.getAllFields(bean.getClass());
            Field[] var10 = fields;
            int var5 = fields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Field field = var10[var6];
                if (field.getClass().isAssignableFrom(Collection.class)) {
                    errorList.addAll(this.validateBean(PropertyUtils.getProperty(bean, field.getName())));
                } else {
                    List<IValidator> validators = this.getValidatorOnField(field);
                    errorList.addAll(this.validateOnStop(bean, validators));
                }
            }
        }

        return errorList;
    }

    private List<ValidateError> validateOnStop(Object bean, List<IValidator> validators) {
        List<ValidateError> validateErrors = new ArrayList();

        for(int i = 0; i < validators.size(); ++i) {
            IValidator validator = (IValidator)validators.get(i);
            List<ValidateError> errors = validator.validate(bean);
            if (errors.size() > 0) {
                return errors;
            }
        }

        return validateErrors;
    }

    private List<IValidator> jsonStrToValidator(String rules) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<IValidator> validators = new ArrayList();
        JSONObject jsonObject = JSONObject.parseObject(rules);
        Set<String> validatorNames = ValidatorRegistry.getValidatorNames();
        Iterator var6 = validatorNames.iterator();

        while(var6.hasNext()) {
            String name = (String)var6.next();
            JSON validatorJson = (JSON)jsonObject.get(name);
            if (validatorJson != null) {
                Class<? extends IValidator> validatorClass = ValidatorRegistry.getValidatorClass(name);
                if (validatorJson instanceof JSONObject) {
                    IValidator validator = (IValidator)objectMapper.readValue(validatorJson.toJSONString(), validatorClass);
                    validators.add(validator);
                } else if (validatorJson instanceof JSONArray) {
                    JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{validatorClass});
                    List<IValidator> validatorList = (List)objectMapper.readValue(validatorJson.toJSONString(), javaType);
                    validators.addAll(validatorList);
                }
            }
        }

        return validators;
    }

    public List<IValidator> getValidatorOnField(Field field) {
        List<IValidator> validators = new ArrayList();
        String fieldName = field.getName();
        Annotation[] ans = field.getAnnotations();

        for(int i = 0; i < ans.length; ++i) {
            Annotation annotation = ans[i];
            if (annotation instanceof NotNull) {
                NotNullValidator notNullValidator = new NotNullValidator();
                notNullValidator.setFields(ImmutableList.of(fieldName));
                validators.add(notNullValidator);
            } else if (annotation instanceof NotEmpty) {
                NotEmptyValidator notEmptyValidator = new NotEmptyValidator();
                notEmptyValidator.setFields(ImmutableList.of(fieldName));
                validators.add(notEmptyValidator);
            } else if (annotation instanceof Regex) {
                RegexValidator regexValidator = new RegexValidator();
                regexValidator.setFields(ImmutableList.of(fieldName));
                regexValidator.setNullable(((Regex)annotation).nullable());
                regexValidator.setPattern(((Regex)annotation).pattern());
                validators.add(regexValidator);
            } else if (annotation instanceof MaxLength) {
                LengthValidator lengthValidator = new LengthValidator();
                lengthValidator.setField(fieldName);
                lengthValidator.setMaxLength(((MaxLength)annotation).value());
                validators.add(lengthValidator);
            } else if (annotation instanceof DateRange) {
                DateRangeValidator dateRangeValidator = new DateRangeValidator();
                dateRangeValidator.setField(fieldName);
                dateRangeValidator.setEarlier(((DateRange)annotation).earlierFiled());
                validators.add(dateRangeValidator);
            } else if (annotation instanceof Lookup) {
                LookupValidator lookupValidator = new LookupValidator();
                lookupValidator.setField(fieldName);
                lookupValidator.setLookupType(((Lookup)annotation).lookupType());
                validators.add(lookupValidator);
            }
        }

        return validators;
    }
}
