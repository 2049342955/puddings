package com.demo.core.exception.error.code;

import com.demo.core.exception.MultiLangException;

public enum BaseErrorsEnum implements BaseErrorEntity {
    WX_ERROR("1001", ""),
    AUTH_ERROR("", ""),
    SERVICE_ERROR("9999", "msg.error.common.service.not.reacheable"),
    RLOCK_HAS_LOCKED("1003", "msg.error.common.rlock_has_locked"),
    RLOCK_TRY_LOCK_ERROR("1004", "msg.error.common.rlock_try_lock_error"),
    RLOCK_GET_CONFIG_ERROR("1005", "msg.error.common.rlock_get_config_error");

    private String errorCode;
    private String messageCode;
    public MultiLangException exception;

    private BaseErrorsEnum(String errorCode, String messageCode) {
        this.errorCode = errorCode;
        this.messageCode = messageCode;
        this.exception = new MultiLangException(errorCode, messageCode);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getMessageCode() {
        return this.messageCode;
    }
}
