package com.demo.core.validation.validator;

public class ValidateError {
    private Object bean;
    private String field;
    private String errorMsg;
    private Object[] args;

    public Object getBean() {
        return this.bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public ValidateError(Object bean, String field, String errorMsg) {
        this.bean = bean;
        this.field = field;
        this.errorMsg = errorMsg;
    }

    public ValidateError(Object bean, String field, String errorMsg, Object[] args) {
        this.bean = bean;
        this.field = field;
        this.errorMsg = errorMsg;
        this.args = args;
    }

    public ValidateError() {
    }

    public static ValidateError generateError(Object bean, String field, String errorMsg) {
        return new ValidateError(bean, field, errorMsg, new Object[0]);
    }

    public static ValidateError generateError(Object bean, String field, String errorMsg, Object[] args) {
        return new ValidateError(bean, field, errorMsg, args);
    }

    public String toString() {
        return "ValidateError{bean=" + this.bean + ", field='" + this.field + '\'' + ", errorMsg='" + this.errorMsg + '\'' + '}';
    }
}
