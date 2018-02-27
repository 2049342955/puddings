package com.demo.core.exception;

import com.demo.core.exception.error.code.BaseErrorEntity;

public class MultiLangException extends RuntimeException {
    private String errorCode;
    private String messageCode;
    private String[] args;

    public MultiLangException(BaseErrorEntity baseErrorEnum, String... args) {
        super(baseErrorEnum.getMessageCode());
        this.errorCode = baseErrorEnum.getErrorCode();
        this.messageCode = baseErrorEnum.getMessageCode();
        this.args = args;
    }

    public MultiLangException(String errorCode, String messageCode) {
        super(messageCode);
        this.errorCode = errorCode;
        this.messageCode = messageCode;
    }

    public MultiLangException(String errorCode, String messageCode, String[] args) {
        super(messageCode);
        this.errorCode = errorCode;
        this.messageCode = messageCode;
        this.args = args;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessageCode() {
        return this.messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String[] getArgs() {
        return this.args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
